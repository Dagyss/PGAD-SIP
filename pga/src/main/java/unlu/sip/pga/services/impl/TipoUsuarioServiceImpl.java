package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.services.TipoUsuarioService;
import unlu.sip.pga.repositories.TipoUsuarioRepository;
import unlu.sip.pga.entities.TipoUsuario;
import java.util.Optional;

@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioService {
    @Autowired private TipoUsuarioRepository repo;

    @Override
    public TipoUsuario crearOActualizar(TipoUsuario tu) {
        return repo.save(tu);
    }
    @Override
    public Optional<TipoUsuario> obtenerPorUsuario(String idUsuario) {
        return repo.findById(idUsuario);
    }
    @Override
    public void eliminarPorUsuario(String idUsuario) {
        repo.deleteById(idUsuario);
    }
}
