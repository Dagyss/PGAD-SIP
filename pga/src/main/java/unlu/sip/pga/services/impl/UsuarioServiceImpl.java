package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.services.UsuarioService;
import unlu.sip.pga.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired private UsuarioRepository usuarioRepository;
    public Usuario crearUsuario(Usuario usuario) { return usuarioRepository.save(usuario); }
    public Optional<Usuario> obtenerUsuarioPorId(Long id) { return usuarioRepository.findById(id); }
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAll(); }
    public Usuario actualizarUsuario(Usuario usuario) { return usuarioRepository.save(usuario); }
    public void eliminarUsuario(Long id) { usuarioRepository.deleteById(id); }
}