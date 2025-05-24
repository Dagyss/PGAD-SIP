package unlu.sip.pga.services;

import unlu.sip.pga.dto.EjercicioDTO;
import unlu.sip.pga.dto.GenerateEjercicioRequestDTO;
import unlu.sip.pga.entities.Ejercicio;
import java.util.List;
import java.util.Optional;

public interface EjercicioService {
    EjercicioDTO generarEjercicio(GenerateEjercicioRequestDTO request) throws Exception;
    Optional<Ejercicio> obtenerEjercicioPorId(Integer id);
    List<Ejercicio> listarEjerciciosPorModulo(Integer moduloId);
    Ejercicio actualizarEjercicio(Ejercicio ejercicio);
    void eliminarEjercicio(Integer id);
}