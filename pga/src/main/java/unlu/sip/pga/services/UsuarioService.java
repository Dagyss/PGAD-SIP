package unlu.sip.pga.services;

import unlu.sip.pga.entities.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorId(String id);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(String id);
    void syncAllUsuarios();
    String obtenerTokenManagementApi();
    Usuario mapearUsuarioAuth0aEntidad(Map userMap);
}