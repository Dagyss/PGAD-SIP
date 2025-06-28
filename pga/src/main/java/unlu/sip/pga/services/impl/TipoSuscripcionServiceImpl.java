package unlu.sip.pga.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unlu.sip.pga.entities.TipoSuscripcion;
import unlu.sip.pga.repositories.TipoSuscripcionRepository;
import unlu.sip.pga.services.TipoSuscripcionService;

@Service
public class TipoSuscripcionServiceImpl implements TipoSuscripcionService {
    @Autowired
    private TipoSuscripcionRepository repo;
    
    public List<TipoSuscripcion> listarTiposSuscripcion() {
        return repo.findAll();
    }

    @Override
    public TipoSuscripcion createTipoSuscripcion(TipoSuscripcion tipoSuscripcion) {
        return repo.save(tipoSuscripcion);
    }

    public Optional<TipoSuscripcion> getTipoSuscripcion(Integer id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteTipoSuscripcion(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
    
}
