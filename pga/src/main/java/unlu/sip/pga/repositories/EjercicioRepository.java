package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Ejercicio;
import java.util.List;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {
    List<Ejercicio> findByModuloId(Integer idModulo);
}
