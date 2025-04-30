package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unlu.sip.pga.entities.ContenidoModulo;
import java.util.List;

@Repository
public interface ContenidoModuloRepository extends JpaRepository<ContenidoModulo, Integer> {
    List<ContenidoModulo> findByModuloId(Integer id);
}
