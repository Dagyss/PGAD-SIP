package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Certificacion;
import java.util.List;

public interface CertificacionRepository extends JpaRepository<Certificacion, Long> {
    List<Certificacion> findByUsuarioId(Long idUsuario);
}
