package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.PermisoDTO;
import unlu.sip.pga.mappers.PermisoMapper;
import unlu.sip.pga.services.PermisoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {
    @Autowired private PermisoService permisoService;
    @Autowired private PermisoMapper permisoMapper;

    @GetMapping
    public List<PermisoDTO> listar() {
        return permisoService.listarPermisos().stream()
                .map(permisoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoDTO> obtener(@PathVariable Integer id) {
        return permisoService.obtenerPermisoPorId(id)
                .map(permisoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PermisoDTO> crear(@RequestBody PermisoDTO dto) {
        PermisoDTO creado = permisoMapper.toDto(
                permisoService.crearPermiso(permisoMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoDTO> actualizar(@PathVariable Integer id, @RequestBody PermisoDTO dto) {
        dto.setIdPermiso(id);
        PermisoDTO actualizado = permisoMapper.toDto(
                permisoService.actualizarPermiso(permisoMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        permisoService.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }
}
