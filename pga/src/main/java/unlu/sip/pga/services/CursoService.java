package unlu.sip.pga.services;

import unlu.sip.pga.dto.CursoDTO;
import unlu.sip.pga.entities.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    Curso crearCurso(Curso curso) throws Exception;
    Optional<CursoDTO> obtenerCursoPorId(Integer id);
    List<CursoDTO> listarCursos();
    Curso actualizarCurso(Curso curso);
    void eliminarCurso(Integer id);
}