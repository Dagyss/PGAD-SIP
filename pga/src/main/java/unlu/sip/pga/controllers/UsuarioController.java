package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.UsuarioDTO;
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
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Integer id) {
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
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        dto.setIdUsuario(id);
        UsuarioDTO actualizado = usuarioMapper.toDto(
                usuarioService.actualizarUsuario(usuarioMapper.toEntity(dto)));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}