package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.ContenidoModulo;
import java.util.List;

public interface ContenidoModuloRepository extends JpaRepository<ContenidoModulo, Long> {
    List<ContenidoModulo> findByModuloId(Long idModulo);
}
