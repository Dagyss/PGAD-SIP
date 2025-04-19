package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import unlu.sip.pga.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}

