package unlu.sip.pga.services;

import unlu.sip.pga.entities.Modulo;
import java.util.List;
import java.util.Optional;

public interface ModuloService {
    Modulo crearModulo(Modulo modulo);
    Optional<Modulo> obtenerModuloPorId(Long id);
    List<Modulo> listarModulosPorCurso(Long cursoId);
    Modulo actualizarModulo(Modulo modulo);
    void eliminarModulo(Long id);
}