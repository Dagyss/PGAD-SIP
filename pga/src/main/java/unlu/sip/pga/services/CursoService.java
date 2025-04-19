package unlu.sip.pga.services;

import unlu.sip.pga.entities.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    Curso crearCurso(Curso curso);
    Optional<Curso> obtenerCursoPorId(Long id);
    List<Curso> listarCursos();
    Curso actualizarCurso(Curso curso);
    void eliminarCurso(Long id);
}