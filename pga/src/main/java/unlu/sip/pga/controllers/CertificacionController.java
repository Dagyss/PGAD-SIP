package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.CertificacionDTO;
import unlu.sip.pga.mappers.CertificacionMapper;
import unlu.sip.pga.services.CertificacionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificaciones")
public class CertificacionController {
    @Autowired private CertificacionService certificacionService;
    @Autowired private CertificacionMapper certificacionMapper;

    @GetMapping
    public List<CertificacionDTO> listar(@RequestParam(required=false) Integer idUsuario) {
        return (idUsuario == null ? certificacionService.listarCertificacionesPorUsuario(null)
                : certificacionService.listarCertificacionesPorUsuario(idUsuario)).stream()
                .map(certificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificacionDTO> obtener(@PathVariable Integer id) {
        return certificacionService.obtenerCertificacionPorId(id)
                .map(certificacionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CertificacionDTO> crear(@RequestBody CertificacionDTO dto) {
        CertificacionDTO creado = certificacionMapper.toDto(
                certificacionService.crearCertificacion(certificacionMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificacionDTO> actualizar(@PathVariable Integer id, @RequestBody CertificacionDTO dto) {
        dto.setId(id);
        CertificacionDTO actualizado = certificacionMapper.toDto(
                certificacionService.actualizarCertificacion(certificacionMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        certificacionService.eliminarCertificacion(id);
        return ResponseEntity.noContent().build();
    }
}
