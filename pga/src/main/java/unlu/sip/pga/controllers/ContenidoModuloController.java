package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.ContenidoModuloDTO;
import unlu.sip.pga.mappers.ContenidoModuloMapper;
import unlu.sip.pga.services.ContenidoModuloService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contenidos")
public class ContenidoModuloController {
    @Autowired private ContenidoModuloService contenidoService;
    @Autowired private ContenidoModuloMapper contenidoMapper;

    @GetMapping
    public List<ContenidoModuloDTO> listar(@RequestParam(required=false) Integer moduloId) {
        return (moduloId == null ? contenidoService.listarContenidosPorModulo(null)
                : contenidoService.listarContenidosPorModulo(moduloId)).stream()
                .map(contenidoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenidoModuloDTO> obtener(@PathVariable Integer id) {
        return contenidoService.obtenerContenidoPorId(id)
                .map(contenidoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContenidoModuloDTO> crear(@RequestBody ContenidoModuloDTO dto) {
        ContenidoModuloDTO creado = contenidoMapper.toDto(
                contenidoService.crearContenido(contenidoMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContenidoModuloDTO> actualizar(@PathVariable Integer id, @RequestBody ContenidoModuloDTO dto) {
        dto.setId(id);
        ContenidoModuloDTO actualizado = contenidoMapper.toDto(
                contenidoService.actualizarContenido(contenidoMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        contenidoService.eliminarContenido(id);
        return ResponseEntity.noContent().build();
    }
}