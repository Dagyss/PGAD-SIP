package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unlu.sip.pga.entities.Certificacion;
import java.util.List;

@Repository
public interface CertificacionRepository extends JpaRepository<Certificacion, Integer> {
    List<Certificacion> findByUsuarioId(String id);
}
