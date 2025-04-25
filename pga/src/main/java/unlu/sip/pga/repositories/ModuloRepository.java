package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Modulo;
import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
     List<Modulo> findByCursoId(Integer id);
}
