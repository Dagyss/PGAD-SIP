package unlu.sip.pga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.EjercicioDTO;
import unlu.sip.pga.dto.GenerateEjercicioRequestDTO;
import unlu.sip.pga.mappers.EjercicioMapper;
import unlu.sip.pga.services.EjercicioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ejercicios")
@RequiredArgsConstructor
public class EjercicioController {
    private final EjercicioService ejercicioService;
    private final EjercicioMapper ejercicioMapper;

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

    @PostMapping("/generate")
    public ResponseEntity<?> generar(@RequestBody GenerateEjercicioRequestDTO req) {
        try {
            EjercicioDTO dto = ejercicioService.generarEjercicio(req);
            return ResponseEntity.ok(dto);
        } catch(Exception e) {
            e.printStackTrace();
            Map<String,String> errorBody = new HashMap<>();
            errorBody.put("error",
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Ocurrió un error inesperado al generar el ejercicio");
            return ResponseEntity
                    .badRequest()
                    .body(errorBody);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EjercicioDTO> actualizar(@PathVariable Integer id, @RequestBody EjercicioDTO dto) {
        dto.setId(id);
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
