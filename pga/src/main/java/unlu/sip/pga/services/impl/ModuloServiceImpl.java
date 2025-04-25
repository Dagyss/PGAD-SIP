package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.services.ModuloService;
import unlu.sip.pga.repositories.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuloServiceImpl implements ModuloService {
    @Autowired private ModuloRepository moduloRepository;
    public Modulo crearModulo(Modulo modulo) { return moduloRepository.save(modulo); }
    public Optional<Modulo> obtenerModuloPorId(Integer id) { return moduloRepository.findById(id); }
    public List<Modulo> listarModulosPorCurso(Integer cursoId) { return moduloRepository.findByCursoId(cursoId); }
    public Modulo actualizarModulo(Modulo modulo) { return moduloRepository.save(modulo); }
    public void eliminarModulo(Integer id) { moduloRepository.deleteById(id); }
}
