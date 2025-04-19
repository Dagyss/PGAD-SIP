package unlu.sip.pga.repositories;

import unlu.sip.pga.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByTitulo(String titulo);
}
