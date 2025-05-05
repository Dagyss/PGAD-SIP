package unlu.sip.pga.repositories;

import unlu.sip.pga.entities.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Integer> {
    List<Progreso> findByUsuarioIdAndCursoId(String idUsuario, Integer idCurso);
}
