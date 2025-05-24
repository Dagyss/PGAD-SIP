package unlu.sip.pga.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unlu.sip.pga.dto.ModuloDTO;
import unlu.sip.pga.entities.Curso;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.mappers.ModuloMapper;
import unlu.sip.pga.repositories.CursoRepository;
import unlu.sip.pga.repositories.ModuloRepository;
import unlu.sip.pga.services.ModuloService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuloServiceImpl implements ModuloService {
    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;
    private final ModuloMapper moduloMapper;

    @Override
    @Transactional
    public Modulo crearModulo(ModuloDTO dto) {
        if (dto.getCursoId() == null) {
            throw new IllegalArgumentException("El campo cursoId es obligatorio");
        }
        // Cargar el Curso real
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado: " + dto.getCursoId()));

        // Mapear DTO a entidad
        Modulo modulo = moduloMapper.toEntity(dto);
        modulo.setCurso(curso);

        return moduloRepository.save(modulo);
    }

    @Override
    public Optional<Modulo> obtenerModuloPorId(Integer id) {
        return moduloRepository.findById(id);
    }

    @Override
    public List<Modulo> listarModulosPorCurso(Integer cursoId) {
        return moduloRepository.findByCursoId(cursoId);
    }

    @Override
    @Transactional
    public Modulo actualizarModulo(ModuloDTO dto) {
        if (dto.getCursoId() == null) {
            throw new IllegalArgumentException("El campo cursoId es obligatorio para actualizar");
        }
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado: " + dto.getCursoId()));

        Modulo modulo = moduloMapper.toEntity(dto);
        modulo.setCurso(curso);

        return moduloRepository.save(modulo);
    }

    @Override
    public void eliminarModulo(Integer id) {
        if (!moduloRepository.existsById(id)) {
            throw new IllegalArgumentException("Módulo no encontrado: " + id);
        }
        moduloRepository.deleteById(id);
    }
}
