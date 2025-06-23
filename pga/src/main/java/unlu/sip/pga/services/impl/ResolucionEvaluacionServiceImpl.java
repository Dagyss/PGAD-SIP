package unlu.sip.pga.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unlu.sip.pga.dto.ResolucionEvaluacionDTO;
import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.entities.ResolucionEvaluacion;
import unlu.sip.pga.mappers.ResolucionEvaluacionMapper;
import unlu.sip.pga.models.enumerados.EstadoResolucion;
import unlu.sip.pga.repositories.ResolucionEvaluacionRepository;
import unlu.sip.pga.repositories.EvaluacionRepository;
import unlu.sip.pga.repositories.UsuarioRepository;
import unlu.sip.pga.services.ResolucionEvaluacionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResolucionEvaluacionServiceImpl implements ResolucionEvaluacionService {
    private final ResolucionEvaluacionRepository repo;
    private final EvaluacionRepository evRepo;
    private final UsuarioRepository userRepo;
    private final ResolucionEvaluacionMapper mapper;

    @Override
    @Transactional
    public ResolucionEvaluacionDTO crear(ResolucionEvaluacionDTO dto) {
        // Cargar referencias
        Evaluacion ev = evRepo.findById(dto.getIdEvaluacion())
                .orElseThrow(() -> new IllegalArgumentException("Evaluación no encontrada: " + dto.getIdEvaluacion()));
        Usuario user = userRepo.findById(dto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + dto.getIdUsuario()));

        ResolucionEvaluacion ent = mapper.toEntity(dto);
        ent.setEvaluacion(ev);
        ent.setUsuario(user);

        ResolucionEvaluacion saved = repo.save(ent);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResolucionEvaluacionDTO> obtenerPorId(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResolucionEvaluacionDTO> listarTodas() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResolucionEvaluacionDTO actualizar(Integer id, ResolucionEvaluacionDTO dto) {
        ResolucionEvaluacion existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resolución no encontrada: " + id));

        existing.setFechaResolucion(dto.getFechaResolucion());
        existing.setResolucion(dto.getResolucion());
        existing.setEstado(EstadoResolucion.valueOf(dto.getEstado()));

        ResolucionEvaluacion upd = repo.save(existing);
        return mapper.toDto(upd);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}