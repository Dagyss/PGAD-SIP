package unlu.sip.pga.controllers;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.dto.ResolucionDTO;
import unlu.sip.pga.entities.Resolucion;
import unlu.sip.pga.services.ResolucionService;
import unlu.sip.pga.mappers.ResolucionMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resoluciones")
public class ResolucionController {

    @Autowired private ResolucionService service;
    @Autowired private ResolucionMapper mapper;

    @PostMapping
    public ResponseEntity<ResolucionDTO> crear(@RequestBody ResolucionDTO dto) {
        Resolucion entidad = mapper.toEntity(dto);
        Resolucion guardada = service.crear(entidad);
        return ResponseEntity.ok(mapper.toDto(guardada));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ResolucionDTO>> listarPorUsuario(@PathVariable String idUsuario) {
        List<ResolucionDTO> list = service.listarPorIdUsuario(idUsuario).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ejercicio/{idEjercicio}")
    public ResponseEntity<List<ResolucionDTO>> listarPorEjercicio(@PathVariable Integer idEjercicio) {
        List<ResolucionDTO> list = service.listarPorIdEjercicio(idEjercicio).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{idResolucion}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idResolucion) {
        service.eliminar(idResolucion);
        return ResponseEntity.noContent().build();
    }
}