package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.EvaluacionDTO;
import unlu.sip.pga.mappers.EvaluacionMapper;
import unlu.sip.pga.services.EvaluacionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {
    @Autowired private EvaluacionService evaluacionService;
    @Autowired private EvaluacionMapper evaluacionMapper;

    @GetMapping
    public List<EvaluacionDTO> listar(@RequestParam(required=false) Integer cursoId) {
        return (cursoId == null ? evaluacionService.listarEvaluacionesPorCurso(null)
                : evaluacionService.listarEvaluacionesPorCurso(cursoId)).stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> obtener(@PathVariable Integer id) {
        return evaluacionService.obtenerEvaluacionPorId(id)
                .map(evaluacionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EvaluacionDTO> crear(@RequestBody EvaluacionDTO dto) {
        EvaluacionDTO creado = evaluacionMapper.toDto(
                evaluacionService.crearEvaluacion(evaluacionMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> actualizar(@PathVariable Integer id, @RequestBody EvaluacionDTO dto) {
        dto.setId(id);
        EvaluacionDTO actualizado = evaluacionMapper.toDto(
                evaluacionService.actualizarEvaluacion(evaluacionMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        evaluacionService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
