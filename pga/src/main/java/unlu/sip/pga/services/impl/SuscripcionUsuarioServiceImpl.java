package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.services.SuscripcionUsuarioService;
import unlu.sip.pga.repositories.SuscripcionUsuarioRepository;
import unlu.sip.pga.entities.SuscripcionUsuario;
import unlu.sip.pga.models.UsuarioSuscripcionId;
import java.util.List;

@Service
public class SuscripcionUsuarioServiceImpl implements SuscripcionUsuarioService {

    @Autowired
    private SuscripcionUsuarioRepository repo;

    @Override
    public SuscripcionUsuario crear(SuscripcionUsuario su) {
        return repo.save(su);
    }

    @Override
    public List<SuscripcionUsuario> listarPorUsuario(String idUsuario) {
        return repo.findByUsuario_Id(idUsuario);
    }

    @Override
    public List<SuscripcionUsuario> listarPorSuscripcion(Integer idSuscripcion) {
        return repo.findBySuscripcion_Id(idSuscripcion);
    }

    @Override
    public void eliminar(String idUsuario, Integer idSuscripcion) {
        repo.deleteById(new UsuarioSuscripcionId(idUsuario, idSuscripcion));
    }
}