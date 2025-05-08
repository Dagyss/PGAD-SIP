package unlu.sip.pga.services.impl;

import java.util.List;
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
    
}
