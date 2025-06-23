package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unlu.sip.pga.dto.CodigoUsuarioDTO;
import unlu.sip.pga.dto.CodigoUsuarioEvaluacionDTO;
import unlu.sip.pga.services.CorregirEjercicioService;
import unlu.sip.pga.services.CorregirEvaluacionService;

@RestController
@RequestMapping("/api/corregir")
public class CorregirEjerciciosController {

    @Autowired
    private CorregirEjercicioService corregirEjercicioService;
    @Autowired
    private CorregirEvaluacionService corregirEvaluacionService;

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

    @PostMapping("/evaluaciones")
    public ResponseEntity<String> enviarTarea(@RequestBody CodigoUsuarioEvaluacionDTO request) {
        try {
            String resultado = corregirEvaluacionService.enviarTarea(request);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
