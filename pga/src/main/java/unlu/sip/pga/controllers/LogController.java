package unlu.sip.pga.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/logs")
public class LogController {
    @PostMapping
    public ResponseEntity<Void> guardarLog(@RequestBody Map<String, Object> log) {
        System.out.println("LOG FRONTEND: " + log);
        return ResponseEntity.ok().build();
    }
    
}
