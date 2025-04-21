package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.ModuloDTO;
import unlu.sip.pga.mappers.ModuloMapper;
import unlu.sip.pga.services.ModuloService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/modulos")
public class ModuloController {
    @Autowired private ModuloService moduloService;
    @Autowired private ModuloMapper moduloMapper;

    @GetMapping
    public List<ModuloDTO> listar(@RequestParam(required=false) Integer cursoId) {
        return (cursoId == null ? moduloService.listarModulosPorCurso(null)
                : moduloService.listarModulosPorCurso(cursoId)).stream()
                .map(moduloMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuloDTO> obtener(@PathVariable Integer id) {
        return moduloService.obtenerModuloPorId(id)
                .map(moduloMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ModuloDTO> crear(@RequestBody ModuloDTO dto) {
        ModuloDTO creado = moduloMapper.toDto(
                moduloService.crearModulo(moduloMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuloDTO> actualizar(@PathVariable Integer id, @RequestBody ModuloDTO dto) {
        dto.setIdModulo(id);
        ModuloDTO actualizado = moduloMapper.toDto(
                moduloService.actualizarModulo(moduloMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        moduloService.eliminarModulo(id);
        return ResponseEntity.noContent().build();
    }
}