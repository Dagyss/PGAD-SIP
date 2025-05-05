package unlu.sip.pga.services;

import unlu.sip.pga.entities.Suscripcion;
import java.util.List;
import java.util.Optional;

public interface SuscripcionService {
    Suscripcion crearSuscripcion(Suscripcion suscripcion);
    Optional<Suscripcion> obtenerSuscripcionPorId(Integer id);
    List<Suscripcion> listarSuscripcionesPorUsuario(String id);
    Suscripcion actualizarSuscripcion(Suscripcion suscripcion);
    void eliminarSuscripcion(Integer id);
}
