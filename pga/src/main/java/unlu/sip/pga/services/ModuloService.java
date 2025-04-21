package unlu.sip.pga.services;

import unlu.sip.pga.entities.Modulo;
import java.util.List;
import java.util.Optional;

public interface ModuloService {
    Modulo crearModulo(Modulo modulo);
    Optional<Modulo> obtenerModuloPorId(Integer id);
    List<Modulo> listarModulosPorCurso(Integer cursoId);
    Modulo actualizarModulo(Modulo modulo);
    void eliminarModulo(Integer id);
}