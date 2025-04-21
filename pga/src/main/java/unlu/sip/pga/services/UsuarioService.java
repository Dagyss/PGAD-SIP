package unlu.sip.pga.services;

import unlu.sip.pga.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorId(Integer id);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer id);
}