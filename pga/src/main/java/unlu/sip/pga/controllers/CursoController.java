package unlu.sip.pga.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.CursoDTO;
import unlu.sip.pga.mappers.CursoMapper;
import unlu.sip.pga.services.CursoService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    @Autowired private CursoService cursoService;
    @Autowired private CursoMapper cursoMapper;

    @GetMapping
    public List<CursoDTO> listar() {
        return cursoService.listarCursos().stream()
                .map(cursoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtener(@PathVariable Integer id) {
        return cursoService.obtenerCursoPorId(id)
                .map(cursoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:course')")
    public ResponseEntity<CursoDTO> crear(@RequestBody CursoDTO dto) {
        CursoDTO curso = cursoMapper.toDto(
                cursoService.crearCurso(cursoMapper.toEntity(dto)));
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizar(@PathVariable Integer id, @RequestBody CursoDTO dto) {
        dto.setId(id);
        CursoDTO actualizado = cursoMapper.toDto(
                cursoService.actualizarCurso(cursoMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}