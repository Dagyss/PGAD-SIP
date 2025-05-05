package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unlu.sip.pga.entities.Evaluacion;
import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
    List<Evaluacion>
    findByUsuarioIdAndCursoId(
            String idUsuario,
            Integer idCurso
    );

    List<Evaluacion> findByCursoId(Integer id);
}
