package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.ResolucionEvaluacion;

import java.util.List;

public interface ResolucionEvaluacionRepository extends JpaRepository<ResolucionEvaluacion, Integer> {
    List<ResolucionEvaluacion> findByUsuarioId(
            String idUsuario
    );

}
