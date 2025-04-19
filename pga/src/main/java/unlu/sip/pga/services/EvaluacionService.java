package unlu.sip.pga.services;

import unlu.sip.pga.entities.Evaluacion;
import java.util.List;
import java.util.Optional;

public interface EvaluacionService {
    Evaluacion crearEvaluacion(Evaluacion evaluacion);
    Optional<Evaluacion> obtenerEvaluacionPorId(Long id);
    List<Evaluacion> listarEvaluacionesPorCurso(Long cursoId);
    Evaluacion actualizarEvaluacion(Evaluacion evaluacion);
    void eliminarEvaluacion(Long id);
}