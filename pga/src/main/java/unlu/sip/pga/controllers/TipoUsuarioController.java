package unlu.sip.pga.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.dto.TipoUsuarioDTO;
import unlu.sip.pga.entities.TipoUsuario;
import unlu.sip.pga.services.TipoUsuarioService;
import unlu.sip.pga.mappers.TipoUsuarioMapper;

@RestController
@RequestMapping("/api/tipo-usuario")
public class TipoUsuarioController {

    @Autowired private TipoUsuarioService service;
    @Autowired private TipoUsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<TipoUsuarioDTO> crearOActualizar(@RequestBody TipoUsuarioDTO dto) {
        TipoUsuario entidad = mapper.toEntity(dto);
        TipoUsuario saved = service.crearOActualizar(entidad);
        return ResponseEntity.ok(mapper.toDto(saved));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<TipoUsuarioDTO> getByUsuario(@PathVariable String idUsuario) {
        return service.obtenerPorUsuario(idUsuario)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable String idUsuario) {
        service.eliminarPorUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}