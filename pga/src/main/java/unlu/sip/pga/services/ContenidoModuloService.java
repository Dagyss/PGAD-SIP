package unlu.sip.pga.services;

import unlu.sip.pga.entities.ContenidoModulo;
import java.util.List;
import java.util.Optional;

public interface ContenidoModuloService {
    ContenidoModulo crearContenido(ContenidoModulo contenido);
    Optional<ContenidoModulo> obtenerContenidoPorId(Long id);
    List<ContenidoModulo> listarContenidosPorModulo(Long moduloId);
    ContenidoModulo actualizarContenido(ContenidoModulo contenido);
    void eliminarContenido(Long id);
}