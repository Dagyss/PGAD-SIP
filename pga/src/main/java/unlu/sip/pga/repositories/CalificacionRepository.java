package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Calificacion;
import unlu.sip.pga.models.CalificacionId;
import java.util.List;

public interface CalificacionRepository
        extends JpaRepository<Calificacion, CalificacionId> {
    List<Calificacion> findByUsuario_Id(String idUsuario);
    List<Calificacion> findByCurso_Id(Integer idCurso);
}