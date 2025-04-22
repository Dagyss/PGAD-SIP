package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.EjercicioDTO;
import unlu.sip.pga.mappers.EjercicioMapper;
import unlu.sip.pga.services.EjercicioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {
    @Autowired private EjercicioService ejercicioService;
    @Autowired private EjercicioMapper ejercicioMapper;

    @GetMapping
    public List<EjercicioDTO> listar(@RequestParam(required=false) Integer moduloId) {
        return (moduloId == null ? ejercicioService.listarEjerciciosPorModulo(null)
                : ejercicioService.listarEjerciciosPorModulo(moduloId)).stream()
                .map(ejercicioMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjercicioDTO> obtener(@PathVariable Integer id) {
        return ejercicioService.obtenerEjercicioPorId(id)
                .map(ejercicioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EjercicioDTO> crear(@RequestBody EjercicioDTO dto) {
        EjercicioDTO creado = ejercicioMapper.toDto(
                ejercicioService.crearEjercicio(ejercicioMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EjercicioDTO> actualizar(@PathVariable Integer id, @RequestBody EjercicioDTO dto) {
        dto.setIdEjercicio(id);
        EjercicioDTO actualizado = ejercicioMapper.toDto(
                ejercicioService.actualizarEjercicio(ejercicioMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ejercicioService.eliminarEjercicio(id);
        return ResponseEntity.noContent().build();
    }
}
