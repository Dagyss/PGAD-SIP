package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.PagoDTO;
import unlu.sip.pga.mappers.PagoMapper;
import unlu.sip.pga.services.PagoService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para pagos, ahora asociando cada pago a un usuario.
 */
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoMapper pagoMapper;


    @GetMapping
    public List<PagoDTO> listar(@RequestParam(required = false) Integer idUsuario) {
        return (idUsuario == null
                ? pagoService.listarPagos()
                : pagoService.listarPagosPorUsuario(idUsuario)
        ).stream()
                .map(pagoMapper::toDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/{paymentId}")
    public ResponseEntity<PagoDTO> obtener(@PathVariable String paymentId) {
        return pagoService.obtenerPagoPorId(paymentId)
                .map(pagoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<PagoDTO> crear(@RequestBody PagoDTO dto) {
        // dto debe incluir el campo idUsuario para la relación
        PagoDTO creado = pagoMapper.toDto(
                pagoService.crearPago(pagoMapper.toEntity(dto))
        );
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PagoDTO> actualizar(
            @PathVariable String paymentId,
            @RequestBody PagoDTO dto
    ) {
        dto.setId(paymentId);
        PagoDTO actualizado = pagoMapper.toDto(
                pagoService.actualizarPago(pagoMapper.toEntity(dto))
        );
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> eliminar(@PathVariable String paymentId) {
        pagoService.eliminarPago(paymentId);
        return ResponseEntity.noContent().build();
    }
}
