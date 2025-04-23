package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Certificacion;
import unlu.sip.pga.services.CertificacionService;
import unlu.sip.pga.repositories.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificacionServiceImpl implements CertificacionService {
    @Autowired private CertificacionRepository certificacionRepository;
    public Certificacion crearCertificacion(Certificacion c) { return certificacionRepository.save(c); }
    public Optional<Certificacion> obtenerCertificacionPorId(Integer id) { return certificacionRepository.findById(id); }
    public List<Certificacion> listarCertificacionesPorUsuario(Integer idUsuario) { return certificacionRepository.findByUsuario_IdUsuario(idUsuario); }
    public Certificacion actualizarCertificacion(Certificacion c) { return certificacionRepository.save(c); }
    public void eliminarCertificacion(Integer id) { certificacionRepository.deleteById(id); }
}
