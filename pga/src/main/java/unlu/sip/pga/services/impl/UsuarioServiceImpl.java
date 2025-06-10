package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import unlu.sip.pga.dto.Auth0UserDTO;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.services.UsuarioService;
import unlu.sip.pga.repositories.UsuarioRepository;
import unlu.sip.pga.mappers.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${auth0.domain}")
    private String auth0Domain;
    @Value("${auth0.clientId}")
    private String auth0ClientId;
    @Value("${auth0.clientSecret}")
    private String auth0ClientSecret;
    private RestTemplate restTemplate = new RestTemplate();
    private String cachedUserRoleId = null;
    public Usuario crearUsuario(Usuario usuario) { return usuarioRepository.save(usuario); }
    public Optional<Usuario> obtenerUsuarioPorId(String id) { return usuarioRepository.findById(id); }
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAll(); }
    public Usuario actualizarUsuario(Usuario usuario) { return usuarioRepository.save(usuario); }
    public void eliminarUsuario(String id) { usuarioRepository.deleteById(id); }

    @Override
    public void syncAllUsuarios() {
        String token = obtenerTokenManagementApi();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        int page = 0;
        while (true) {
            String url = String.format("https://%s/api/v2/users?per_page=50&page=%d", auth0Domain, page);
            ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
            Map[] auth0Users = response.getBody();
            if (auth0Users == null || auth0Users.length == 0) {
                break;
            }
            for (Map userMap : auth0Users) {
                Usuario u = mapearUsuarioAuth0aEntidad(userMap);
                usuarioRepository.save(u);
            }
            page++;
        }
    }



    @Override
    public String obtenerTokenManagementApi() {
        Map<String, String> req = new HashMap<>();
        req.put("grant_type", "client_credentials");
        req.put("client_id", auth0ClientId);
        req.put("client_secret", auth0ClientSecret);
        req.put("audience", "https://" + auth0Domain + "/api/v2/");
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,String>> request = new HttpEntity<>(req, h);
        ResponseEntity<Map> resp = restTemplate.postForEntity(
                "https://" + auth0Domain + "/oauth/token", request, Map.class);
        @SuppressWarnings("unchecked")
        String token = (String) resp.getBody().get("access_token");
        return token;
    }

    @Override
    public Usuario mapearUsuarioAuth0aEntidad(Map userMap) {
        Auth0UserDTO auth0dto = objectMapper.convertValue(userMap, Auth0UserDTO.class);
        return usuarioMapper.fromAuth0(auth0dto);
    }

    private String obtenerUserRoleId(String token) {
        if (cachedUserRoleId != null) {
            return cachedUserRoleId;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = String.format("https://%s/api/v2/roles?name_filter=user", auth0Domain);
        ResponseEntity<List> resp = restTemplate.exchange(
                url, HttpMethod.GET, entity, List.class);

        List<Map<String,Object>> roles = resp.getBody();
        if (roles == null || roles.isEmpty()) {
            throw new RuntimeException("No se encontró el rol 'user' en Auth0");
        }
        String roleId = (String) roles.get(0).get("id");
        cachedUserRoleId = roleId;
        return roleId;
    }

    /** Asigna el rol dado a un usuario de Auth0 */
    private void asignarRolEnAuth0(String auth0UserId, String roleId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("roles", List.of(roleId));

        String url = String.format("https://%s/api/v2/users/%s/roles", auth0Domain, auth0UserId);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, request, Void.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(
                    "Error asignando rol en Auth0: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
        }
    }

    @Override
    public Usuario syncUsuarioPorId(String auth0Id) {
        // 1) Obtengo token
        String token = obtenerTokenManagementApi();

        // 2) Recupero el usuario de Auth0 y lo guardo localmente
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String urlUser = String.format("https://%s/api/v2/users/%s", auth0Domain, auth0Id);

        Map userMap;
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    urlUser, HttpMethod.GET, entity, Map.class);
            userMap = response.getBody();
        } catch (HttpClientErrorException.NotFound nf) {
            throw new RuntimeException("404: Usuario Auth0 no encontrado");
        }

        if (userMap == null || userMap.isEmpty()) {
            throw new RuntimeException("Auth0 devolvió un body vacío");
        }

        Auth0UserDTO auth0dto = objectMapper.convertValue(userMap, Auth0UserDTO.class);
        Usuario u = usuarioMapper.fromAuth0(auth0dto);
        Usuario guardado = usuarioRepository.save(u);

        // 3) Obtengo el ID del rol "user" y se lo asigno en Auth0
        String roleId = obtenerUserRoleId(token);
        asignarRolEnAuth0(auth0Id, roleId, token);

        return guardado;
    }
}