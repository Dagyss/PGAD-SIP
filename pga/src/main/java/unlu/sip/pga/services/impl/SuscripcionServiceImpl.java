package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Suscripcion;
import unlu.sip.pga.services.SuscripcionService;
import unlu.sip.pga.repositories.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuscripcionServiceImpl implements SuscripcionService {
    @Autowired private SuscripcionRepository suscripcionRepository;
    public Suscripcion crearSuscripcion(Suscripcion s) { return suscripcionRepository.save(s); }
    public Optional<Suscripcion> obtenerSuscripcionPorId(Integer id) { return suscripcionRepository.findById(id); }
    public List<Suscripcion> listarSuscripcionesPorUsuario(Integer idUsuario) { return suscripcionRepository.findByUsuarioId(idUsuario); }
    public Suscripcion actualizarSuscripcion(Suscripcion s) { return suscripcionRepository.save(s); }
    public void eliminarSuscripcion(Integer id) { suscripcionRepository.deleteById(id); }
}
