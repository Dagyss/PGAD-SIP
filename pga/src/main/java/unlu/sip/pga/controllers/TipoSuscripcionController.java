package unlu.sip.pga.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import unlu.sip.pga.dto.TipoSuscripcionDTO;
import unlu.sip.pga.entities.TipoSuscripcion;
import unlu.sip.pga.mappers.TipoSuscripcionMapper;
import unlu.sip.pga.services.TipoSuscripcionService;


@Controller
@RequestMapping("/api/tiposSuscripcion")
public class TipoSuscripcionController {

    @Autowired
    private TipoSuscripcionService service;

    @GetMapping()
    public ResponseEntity<TipoSuscripcionDTO> getTiposSuscripcion() {
        List<TipoSuscripcion> tiposSuscripcion = service.listarTiposSuscripcion();

        if (tiposSuscripcion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TipoSuscripcionDTO> dtos = tiposSuscripcion.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos); 
    }
    
}
