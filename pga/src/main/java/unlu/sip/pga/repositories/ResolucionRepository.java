package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Resolucion;
import java.util.List;

public interface ResolucionRepository
        extends JpaRepository<Resolucion, Integer> {
    List<Resolucion> findByUsuario_Id(String idUsuario);
    List<Resolucion> findByEjercicio_Id(Integer idEjercicio);
}