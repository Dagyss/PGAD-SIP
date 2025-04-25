package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.services.EvaluacionService;
import unlu.sip.pga.repositories.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {
    @Autowired private EvaluacionRepository evaluacionRepository;
    public Evaluacion crearEvaluacion(Evaluacion ev) { return evaluacionRepository.save(ev); }
    public Optional<Evaluacion> obtenerEvaluacionPorId(Integer idEvaluacion) { return evaluacionRepository.findById(idEvaluacion); }
    public List<Evaluacion> listarEvaluacionesPorCurso(Integer idCurso) { return evaluacionRepository.findByCursoId(idCurso); }
    public Evaluacion actualizarEvaluacion(Evaluacion ev) { return evaluacionRepository.save(ev); }
    public void eliminarEvaluacion(Integer id) { evaluacionRepository.deleteById(id); }
}