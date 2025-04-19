package unlu.sip.pga.services;

import unlu.sip.pga.entities.Progreso;

import java.util.List;
import java.util.Optional;

public interface ProgresoService {
    Progreso crearProgreso(Progreso progreso);
    Optional<Progreso> obtenerProgresoPorId(Long id);
    List<Progreso> listarProgresosPorUsuarioCurso(Long usuarioId, Long cursoId);
    Progreso actualizarProgreso(Progreso progreso);
    void eliminarProgreso(Long id);
}