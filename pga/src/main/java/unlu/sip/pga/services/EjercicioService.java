package unlu.sip.pga.services;

import unlu.sip.pga.entities.Ejercicio;
import java.util.List;
import java.util.Optional;

public interface EjercicioService {
    Ejercicio crearEjercicio(Ejercicio ejercicio);
    Optional<Ejercicio> obtenerEjercicioPorId(Long id);
    List<Ejercicio> listarEjerciciosPorModulo(Long moduloId);
    Ejercicio actualizarEjercicio(Ejercicio ejercicio);
    void eliminarEjercicio(Long id);
}