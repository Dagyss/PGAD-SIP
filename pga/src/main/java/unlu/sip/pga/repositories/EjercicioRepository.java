package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unlu.sip.pga.entities.Ejercicio;
import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {
    List<Ejercicio> findByModuloId(Integer id);
}
