package unlu.sip.pga.services;

import unlu.sip.pga.dto.EvaluacionDTO;
import unlu.sip.pga.dto.GenerateEvaluacionRequestDTO;
import unlu.sip.pga.entities.Evaluacion;
import java.util.List;
import java.util.Optional;

public interface EvaluacionService {
    EvaluacionDTO crearEvaluacion(GenerateEvaluacionRequestDTO evaluacion) throws Exception;
    Optional<Evaluacion> obtenerEvaluacionPorId(Integer id);
    List<Evaluacion> listarEvaluacionesPorCurso(Integer cursoId);
    Evaluacion actualizarEvaluacion(Evaluacion evaluacion);
    void eliminarEvaluacion(Integer id);
    String obtenerTestsPorEvaluacionId(Integer idEvaluacion) throws Exception;
}