package unlu.sip.pga.repositories;

import unlu.sip.pga.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, String> {
    List<Pago> findByUsuarioId(String id);
}
