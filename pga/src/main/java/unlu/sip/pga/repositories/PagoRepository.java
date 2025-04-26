package unlu.sip.pga.repositories;

import unlu.sip.pga.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, String> {
    List<Pago> findByUsuarioId(String id);
}
