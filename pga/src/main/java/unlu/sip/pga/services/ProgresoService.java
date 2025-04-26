package unlu.sip.pga.services;

import unlu.sip.pga.entities.Progreso;

import java.util.List;
import java.util.Optional;

public interface ProgresoService {
    Progreso crearProgreso(Progreso progreso);
    Optional<Progreso> obtenerProgresoPorId(Integer id);
    List<Progreso> listarProgresosPorUsuarioCurso(String idUsuario, Integer cursoId);
    Progreso actualizarProgreso(Progreso progreso);
    void eliminarProgreso(Integer id);
}