package unlu.sip.pga.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unlu.sip.pga.dto.CategoriaDTO;
import unlu.sip.pga.entities.Categoria;
import unlu.sip.pga.mappers.CategoriaMapper;
import unlu.sip.pga.repositories.CategoriaRepository;
import unlu.sip.pga.services.CategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository repo;
    private final CategoriaMapper mapper;

    @Override
    public CategoriaDTO crear(CategoriaDTO dto) {
        Categoria entidad = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entidad));
    }

    @Override
    public CategoriaDTO actualizar(Integer id, CategoriaDTO dto) {
        Categoria existente = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id));
        existente.setNombre(dto.getNombre());
        return mapper.toDto(repo.save(existente));
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public CategoriaDTO obtenerPorId(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id));
    }

    @Override
    public List<CategoriaDTO> listarTodas() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}