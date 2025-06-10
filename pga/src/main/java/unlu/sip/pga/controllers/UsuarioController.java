package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.UsuarioDTO;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.mappers.UsuarioMapper;
import unlu.sip.pga.services.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioMapper usuarioMapper;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listarUsuarios().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable String id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuarioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO dto) {
        UsuarioDTO creado = usuarioMapper.toDto(
                usuarioService.crearUsuario(usuarioMapper.toEntity(dto)));
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable String id, @RequestBody UsuarioDTO dto) {
        dto.setId(id);
        UsuarioDTO actualizado = usuarioMapper.toDto(
                usuarioService.actualizarUsuario(usuarioMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sincronizar")
    public ResponseEntity<?> sincronizarUsuarios(@RequestParam(required = false) String auth0Id) {
        try {
            if (auth0Id != null && !auth0Id.isEmpty()) {
                Usuario u = usuarioService.syncUsuarioPorId(auth0Id);
                UsuarioDTO dto = usuarioMapper.toDto(u);
                return ResponseEntity.ok(dto);
            } else {
                usuarioService.syncAllUsuarios();
                return ResponseEntity.ok("Sincronización completada");
            }
        } catch (RuntimeException ex) {
            String msg = ex.getMessage();
            if (msg != null && msg.toLowerCase().contains("404")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario Auth0 no encontrado: " + auth0Id);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sincronizando usuario(s): " + ex.getMessage());
        }
    }
}