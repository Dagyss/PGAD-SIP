package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}