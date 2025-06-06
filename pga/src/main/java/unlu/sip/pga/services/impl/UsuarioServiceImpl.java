package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    @Override
    public Usuario syncUsuarioPorId(String auth0Id) {
        // 1) Obtengo el token de Auth0 Management API
        String token = obtenerTokenManagementApi();

        // 2) Preparo headers con Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // 3) Construyo la URL para traer el user puntual
        String url = String.format("https://%s/api/v2/users/%s", auth0Domain, auth0Id);

        // 4) Hago la llamada GET y espero un Map (JSON -> Map)
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        // 5) Si no está el body o viene vacío, lanzo excepción
        Map userMap = response.getBody();
        if (userMap == null || userMap.isEmpty()) {
            throw new RuntimeException("No se encontró el usuario en Auth0 o la respuesta fue vacía");
        }

        // 6) Mapeo el Map a Auth0UserDTO usando ObjectMapper
        Auth0UserDTO auth0dto = objectMapper.convertValue(userMap, Auth0UserDTO.class);

        // 7) Convierto el DTO a mi entidad Usuario
        Usuario u = usuarioMapper.fromAuth0(auth0dto);

        // 8) Guardo en la base local y devuelvo el Usuario guardado
        return usuarioRepository.save(u);
    }
}