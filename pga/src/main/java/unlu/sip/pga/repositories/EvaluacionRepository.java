package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Evaluacion;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
    List<Evaluacion> findByUsuarioIdAndCursoId(Integer idUsuario, Integer idCurso);

    List<Evaluacion> findByCursoId(Integer cursoId);
}
