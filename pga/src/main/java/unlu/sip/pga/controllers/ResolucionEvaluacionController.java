package unlu.sip.pga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.ResolucionEvaluacionDTO;
import unlu.sip.pga.services.ResolucionEvaluacionService;

import java.util.List;

@RestController
@RequestMapping("/api/resoluciones/evaluaciones")
@RequiredArgsConstructor
public class ResolucionEvaluacionController {
    private final ResolucionEvaluacionService service;

    @PostMapping
    public ResponseEntity<ResolucionEvaluacionDTO> crear(@RequestBody ResolucionEvaluacionDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResolucionEvaluacionDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ResolucionEvaluacionDTO> listar() {
        return service.listarTodas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResolucionEvaluacionDTO> actualizar(@PathVariable Integer id,
                                                              @RequestBody ResolucionEvaluacionDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
