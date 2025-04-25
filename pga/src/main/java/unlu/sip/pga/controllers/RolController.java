package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.RolDTO;
import unlu.sip.pga.mappers.RolMapper;
import unlu.sip.pga.services.RolService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    @Autowired private RolService rolService;
    @Autowired private RolMapper rolMapper;

    @GetMapping
    public List<RolDTO> listar() {
        return rolService.listarRoles().stream()
                .map(rolMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtener(@PathVariable Integer id) {
        return rolService.obtenerRolPorId(id)
                .map(rolMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RolDTO> crear(@RequestBody RolDTO dto) {
        RolDTO creado = rolMapper.toDto(
                rolService.crearRol(rolMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizar(@PathVariable Integer id, @RequestBody RolDTO dto) {
        dto.setId(id);
        RolDTO actualizado = rolMapper.toDto(
                rolService.actualizarRol(rolMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}