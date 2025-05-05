package unlu.sip.pga.services;


import unlu.sip.pga.entities.Resolucion;
import java.util.List;
import java.util.Optional;

public interface ResolucionService {
    Resolucion crear(Resolucion r);
    Optional<Resolucion> obtenerPorId(Integer idResolucion);
    List<Resolucion> listarPorIdUsuario(String idUsuario);
    List<Resolucion> listarPorIdEjercicio(Integer idEjercicio);
    void eliminar(Integer idResolucion);
}