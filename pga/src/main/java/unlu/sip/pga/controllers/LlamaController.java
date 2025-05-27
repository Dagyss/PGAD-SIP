package unlu.sip.pga.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/llama")
public class LlamaController {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> askModel(@RequestBody Map<String, String> body)
                            throws IOException, InterruptedException {
        String prompt = body.get("prompt");
        if (prompt == null || prompt.isBlank()) {
            return ResponseEntity.badRequest().body("Prompt vacío o nulo");
        }
        
        String jsonRequest = objectMapper.writeValueAsString(Map.of(
            "model", "tinyllama",
            //"model", "mistral:7b-instruct",
            "prompt", prompt,
            "n_predict", 128
        ));

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create("http://ollama:11434/api/generate"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("respuesta del modelo: " + resp.body() );
        JsonNode json = objectMapper.readTree(resp.body());
        String content = json.get("content").asText();
        
        return ResponseEntity.ok(content);
    }
}
