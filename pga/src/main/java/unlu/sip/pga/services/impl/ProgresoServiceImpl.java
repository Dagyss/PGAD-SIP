package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Progreso;
import unlu.sip.pga.services.ProgresoService;
import unlu.sip.pga.repositories.ProgresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgresoServiceImpl implements ProgresoService {
    @Autowired private ProgresoRepository progresoRepository;
    public Progreso crearProgreso(Progreso p) { return progresoRepository.save(p); }
    public Optional<Progreso> obtenerProgresoPorId(Integer id) { return progresoRepository.findById(id); }
    @Override
    public List<Progreso> listarProgresosPorUsuarioCurso(String idUsuario, Integer idCurso) {
        if (idUsuario != null && idCurso != null) {
            return progresoRepository.findByUsuarioIdAndCursoId(idUsuario, idCurso);
        } else if (idUsuario != null) {
            return progresoRepository.findByUsuarioId(idUsuario);
        } else if (idCurso != null) {
            return progresoRepository.findByCursoId(idCurso);
        } else {
            return progresoRepository.findAll();
        }
    }

    public Progreso actualizarProgreso(Progreso p) { return progresoRepository.save(p); }
    public void eliminarProgreso(Integer id) { progresoRepository.deleteById(id); }
}