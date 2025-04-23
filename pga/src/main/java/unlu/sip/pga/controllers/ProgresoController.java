package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.ProgresoDTO;
import unlu.sip.pga.mappers.ProgresoMapper;
import unlu.sip.pga.services.ProgresoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progresos")
public class ProgresoController {
    @Autowired private ProgresoService progresoService;
    @Autowired private ProgresoMapper progresoMapper;

    @GetMapping
    public List<ProgresoDTO> listar(
            @RequestParam(required=false) Integer idUsuario,
            @RequestParam(required=false) Integer cursoId) {
        return progresoService.listarProgresosPorUsuarioCurso(idUsuario, cursoId).stream()
                .map(progresoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoDTO> obtener(@PathVariable Integer id) {
        return progresoService.obtenerProgresoPorId(id)
                .map(progresoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgresoDTO> crear(@RequestBody ProgresoDTO dto) {
        ProgresoDTO creado = progresoMapper.toDto(
                progresoService.crearProgreso(progresoMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresoDTO> actualizar(@PathVariable Integer id, @RequestBody ProgresoDTO dto) {
        dto.setIdProgreso(id);
        ProgresoDTO actualizado = progresoMapper.toDto(
                progresoService.actualizarProgreso(progresoMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        progresoService.eliminarProgreso(id);
        return ResponseEntity.noContent().build();
    }
}
