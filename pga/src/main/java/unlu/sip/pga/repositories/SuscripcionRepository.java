package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unlu.sip.pga.entities.Suscripcion;
import java.util.List;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {
    List<Suscripcion> findByUsuarioId(String idUsuario);
}