package unlu.sip.pga.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.services.SuscripcionUsuarioService;
import unlu.sip.pga.mappers.SuscripcionUsuarioMapper;
import unlu.sip.pga.dto.SuscripcionUsuarioDTO;
import unlu.sip.pga.entities.SuscripcionUsuario;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario-suscripciones")
public class SuscripcionUsuarioController {

    private final SuscripcionUsuarioService service;
    private final SuscripcionUsuarioMapper mapper;

    @Autowired
    public SuscripcionUsuarioController(SuscripcionUsuarioService service,
                                        SuscripcionUsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public SuscripcionUsuarioDTO crear(@RequestBody SuscripcionUsuarioDTO dto) {
        SuscripcionUsuario entidad = mapper.toEntity(dto);
        SuscripcionUsuario creada = service.crear(entidad);
        return mapper.toDto(creada);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<SuscripcionUsuarioDTO> listarPorUsuario(@PathVariable String idUsuario) {
        return service.listarPorUsuario(idUsuario).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/suscripcion/{idSuscripcion}")
    public List<SuscripcionUsuarioDTO> listarPorSuscripcion(@PathVariable Integer idSuscripcion) {
        return service.listarPorSuscripcion(idSuscripcion).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{idUsuario}/{idSuscripcion}")
    public void eliminar(@PathVariable String idUsuario,
                         @PathVariable Integer idSuscripcion) {
        service.eliminar(idUsuario, idSuscripcion);
    }
}