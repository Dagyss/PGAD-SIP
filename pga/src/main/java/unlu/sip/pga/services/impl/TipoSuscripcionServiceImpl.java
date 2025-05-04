package unlu.sip.pga.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import unlu.sip.pga.entities.TipoSuscripcion;
import unlu.sip.pga.repositories.TipoSuscripcionRepository;
import unlu.sip.pga.services.TipoSuscripcionService;

public class TipoSuscripcionServiceImpl implements TipoSuscripcionService {
    @Autowired
    private TipoSuscripcionRepository repo;
    
    public List<TipoSuscripcion> listarTiposSuscripcion() {
        return repo.findAll();
    }
    
}
