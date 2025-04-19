package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Evaluacion;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByUsuarioIdAndCursoId(Long idUsuario, Long idCurso);

    List<Evaluacion> findByCursoId(Long cursoId);
}
