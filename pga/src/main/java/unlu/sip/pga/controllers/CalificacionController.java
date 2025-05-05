package unlu.sip.pga.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.entities.Curso;
import unlu.sip.pga.services.CalificacionService;
import unlu.sip.pga.dto.CalificacionDTO;
import unlu.sip.pga.entities.Calificacion;
import unlu.sip.pga.models.CalificacionId;
import unlu.sip.pga.mappers.CalificacionMapper;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired private CalificacionService service;
    @Autowired private CalificacionMapper mapper;

    @PostMapping
    public ResponseEntity<CalificacionDTO> crear(@RequestBody CalificacionDTO dto) {
        Calificacion entidad = mapper.toEntity(dto);
        Calificacion guardada = service.crear(entidad);
        return ResponseEntity.ok(mapper.toDto(guardada));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<CalificacionDTO>> listarPorUsuario(@PathVariable String idUsuario) {
        List<CalificacionDTO> list = service.listarPorUsuario(idUsuario).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<CalificacionDTO>> listarPorCurso(@PathVariable Integer idCurso) {
        List<CalificacionDTO> list = service.listarPorCurso(idCurso).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{idCurso}/{idUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idCurso,
                                         @PathVariable String idUsuario) {
        service.eliminar(idCurso, idUsuario);
        return ResponseEntity.noContent().build();
    }
}