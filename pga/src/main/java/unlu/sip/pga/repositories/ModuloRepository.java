package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unlu.sip.pga.entities.Modulo;
import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
     List<Modulo> findByCursoId(Integer id);
}
