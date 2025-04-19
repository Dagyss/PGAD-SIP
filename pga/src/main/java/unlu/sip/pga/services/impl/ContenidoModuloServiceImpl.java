package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.ContenidoModulo;
import unlu.sip.pga.services.ContenidoModuloService;
import unlu.sip.pga.repositories.ContenidoModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenidoModuloServiceImpl implements ContenidoModuloService {
    @Autowired private ContenidoModuloRepository contenidoRepository;
    public ContenidoModulo crearContenido(ContenidoModulo contenido) { return contenidoRepository.save(contenido); }
    public Optional<ContenidoModulo> obtenerContenidoPorId(Long id) { return contenidoRepository.findById(id); }
    public List<ContenidoModulo> listarContenidosPorModulo(Long moduloId) { return contenidoRepository.findByModuloId(moduloId); }
    public ContenidoModulo actualizarContenido(ContenidoModulo contenido) { return contenidoRepository.save(contenido); }
    public void eliminarContenido(Long id) { contenidoRepository.deleteById(id); }
}
