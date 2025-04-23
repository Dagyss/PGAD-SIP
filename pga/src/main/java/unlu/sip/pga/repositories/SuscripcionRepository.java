package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Suscripcion;
import java.util.List;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {
    List<Suscripcion> findByUsuario_IdUsuario(Integer idUsuario);
}