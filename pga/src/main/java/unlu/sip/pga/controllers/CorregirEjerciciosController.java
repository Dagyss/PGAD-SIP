package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unlu.sip.pga.dto.CodigoUsuarioDTO;
import unlu.sip.pga.services.CorregirEjercicioService;

@RestController
@RequestMapping("/api/corregir")
public class CorregirEjerciciosController {

    @Autowired
    private CorregirEjercicioService corregirEjercicioService;

    @PostMapping
    public ResponseEntity<String> enviarTarea(@RequestBody CodigoUsuarioDTO request) {
        try {
            String resultado = corregirEjercicioService.enviarTarea(request);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
