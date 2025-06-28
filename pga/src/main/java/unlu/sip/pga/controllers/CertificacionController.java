package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.CertificacionDTO;
import unlu.sip.pga.mappers.CertificacionMapper;
import unlu.sip.pga.services.CertificacionService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificaciones")
public class CertificacionController {
    @Autowired private CertificacionService certificacionService;
    @Autowired private CertificacionMapper certificacionMapper;

    @GetMapping
    public List<CertificacionDTO> listar(@RequestParam(required=false) String idUsuario) {
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

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> emitirPdf(@PathVariable Integer id) {
        return certificacionService.obtenerCertificacionPorId(id)
                .map(cert -> {
                    byte[] pdf = certificacionService.generarPdf(cert);
                    String nombreArchivo = "certificado_" + cert.getUsuario().getNombre().replaceAll("\\s+", "_") + ".pdf";

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.setContentDisposition(ContentDisposition
                            .attachment()
                            .filename(nombreArchivo)
                            .build());

                    return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
