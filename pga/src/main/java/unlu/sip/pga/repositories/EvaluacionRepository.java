package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unlu.sip.pga.entities.Evaluacion;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
    @Query("""
        SELECT e
        FROM Evaluacion e
        LEFT JOIN FETCH e.evaluacionTests t
        WHERE e.curso.id = :cursoId
    """)
    List<Evaluacion> findByCursoIdWithTests(@Param("cursoId") Integer cursoId);
}