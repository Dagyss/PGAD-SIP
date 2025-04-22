package unlu.sip.pga.services;

import unlu.sip.pga.entities.ContenidoModulo;
import java.util.List;
import java.util.Optional;

public interface ContenidoModuloService {
    ContenidoModulo crearContenido(ContenidoModulo contenido);
    Optional<ContenidoModulo> obtenerContenidoPorId(Integer id);
    List<ContenidoModulo> listarContenidosPorModulo(Integer moduloId);
    ContenidoModulo actualizarContenido(ContenidoModulo contenido);
    void eliminarContenido(Integer id);
}