package unlu.sip.pga.services;

import unlu.sip.pga.entities.Certificacion;
import java.util.List;
import java.util.Optional;

public interface CertificacionService  {
    Certificacion crearCertificacion(Certificacion certificacion);
    Optional<Certificacion> obtenerCertificacionPorId(Integer id);
    List<Certificacion> listarCertificacionesPorUsuario(Integer idUsuario);
    Certificacion actualizarCertificacion(Certificacion certificacion);
    void eliminarCertificacion(Integer id);
}
