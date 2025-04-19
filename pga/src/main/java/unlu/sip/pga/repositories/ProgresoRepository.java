package unlu.sip.pga.repositories;

import unlu.sip.pga.entities.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
    List<Progreso> findByUsuarioIdAndCursoId(Long idUsuario, Long idCurso);
}
