// 1. CursoRepository.java
package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unlu.sip.pga.entities.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    @Query("SELECT c FROM Curso c " +
            "LEFT JOIN FETCH c.categorias " +
            "LEFT JOIN FETCH c.modulos m " +
            "LEFT JOIN FETCH m.ejercicios " +
            "WHERE c.id = :id")
    Optional<Curso> findByIdWithModulesAndExercises(@Param("id") Integer id);

    @Query("""
        SELECT DISTINCT c
        FROM Curso c
        LEFT JOIN FETCH c.categorias
        LEFT JOIN FETCH c.modulos m
        LEFT JOIN FETCH m.ejercicios
    """)
    List<Curso> findAllWithModulesAndExercises();
}