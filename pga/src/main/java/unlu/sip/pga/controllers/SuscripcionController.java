package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unlu.sip.pga.dto.SuscripcionDTO;
import unlu.sip.pga.mappers.SuscripcionMapper;
import unlu.sip.pga.services.SuscripcionService;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {
    @Autowired private SuscripcionService suscripcionService;
    @Autowired private SuscripcionMapper suscripcionMapper;

    // @GetMapping
    // public List<TipoSuscripcionDTO> listar(@RequestParam(required=false) String idUsuario) {
    //     return (idUsuario == null ? suscripcionService.listarSuscripcionesPorUsuario(null)
    //             : suscripcionService.listarSuscripcionesPorUsuario(idUsuario)).stream()
    //             .map(suscripcionMapper::toDto)
    //             .collect(Collectors.toList());
    // }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionDTO> obtener(@PathVariable Integer id) {
        return suscripcionService.obtenerSuscripcionPorId(id)
                .map(suscripcionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SuscripcionDTO> crear(@RequestBody SuscripcionDTO dto) {
        SuscripcionDTO creado = suscripcionMapper.toDto(
                suscripcionService.crearSuscripcion(suscripcionMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionDTO> actualizar(@PathVariable Integer id, @RequestBody SuscripcionDTO dto) {
        dto.setId(id);
        SuscripcionDTO actualizado = suscripcionMapper.toDto(
                suscripcionService.actualizarSuscripcion(suscripcionMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        suscripcionService.eliminarSuscripcion(id);
        return ResponseEntity.noContent().build();
    }
}

