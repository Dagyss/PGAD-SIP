package unlu.sip.pga.services;

import org.springframework.security.core.Authentication;
import unlu.sip.pga.dto.CursoDTO;
import unlu.sip.pga.entities.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    CursoDTO crearCurso(Curso curso, Authentication auth) throws Exception;
    Optional<CursoDTO> obtenerCursoPorId(Integer id);
    List<CursoDTO> listarCursos();
    Curso actualizarCurso(Curso curso);
    void eliminarCurso(Integer id);
}