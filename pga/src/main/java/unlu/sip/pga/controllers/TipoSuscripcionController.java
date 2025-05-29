package unlu.sip.pga.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import unlu.sip.pga.dto.TipoSuscripcionDTO;
import unlu.sip.pga.entities.TipoSuscripcion;
import unlu.sip.pga.mappers.TipoSuscripcionMapper;
import unlu.sip.pga.services.TipoSuscripcionService;

@RestController
@RequestMapping("/api/tiposSuscripcion")
public class TipoSuscripcionController {

    @Autowired
    private TipoSuscripcionService service;

    @Autowired
    private TipoSuscripcionMapper mapper;

    @GetMapping()
    public ResponseEntity<List<TipoSuscripcionDTO>> getTiposSuscripcion() {
        List<TipoSuscripcion> tiposSuscripcion = service.listarTiposSuscripcion();

        if (tiposSuscripcion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TipoSuscripcionDTO> dtos = tiposSuscripcion.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos); 
    }

    @PostMapping()
    public ResponseEntity<TipoSuscripcionDTO> createTipoSuscripcion(@RequestBody TipoSuscripcionDTO tipoSuscripcionDTO) {
        TipoSuscripcion tipoSuscripcion = mapper.toEntity(tipoSuscripcionDTO);
        TipoSuscripcion savedTipoSuscripcion = service.createTipoSuscripcion(tipoSuscripcion);
        TipoSuscripcionDTO savedTipoSuscripcionDTO = mapper.toDto(savedTipoSuscripcion);
        return ResponseEntity.status(201).body(savedTipoSuscripcionDTO);
    }
    
}
