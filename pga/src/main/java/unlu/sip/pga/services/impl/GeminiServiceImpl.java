package unlu.sip.pga.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import java.util.List;
import unlu.sip.pga.services.LlamaService;

@Service
public class GeminiServiceImpl implements LlamaService {
    private final RestTemplate rest = new RestTemplate();
    // Usamos el parámetro key en la URL para autenticarnos
    private final String geminiUrl =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"; //Comprobar validez
    @Value("gemini.apiKey")
    String apiKey;
    @Override
    public String generarTextoEjercicio(String prompt) throws Exception {

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("La variable GEMINI_API_KEY no está configurada");
        }

        // Construir body según la spec de Gemini
        Map<String,Object> part = Map.of("text", prompt);
        Map<String,Object> content = Map.of("parts", List.of(part));
        Map<String,Object> body = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Agregar key como parámetro
        String urlConKey = geminiUrl + "?key=" + apiKey;

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> resp = rest.postForEntity(urlConKey, request, Map.class);

        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error llamando a Gemini: " + resp.getStatusCode());
        }

        @SuppressWarnings("unchecked")
        List<Map<String,Object>> candidates =
                (List<Map<String,Object>>) resp.getBody().get("candidates");

        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("Gemini no devolvió candidates");
        }

        // Según la spec, el texto generado viene en el campo "content"
        Object generated = candidates.get(0).get("content");
        if (!(generated instanceof String)) {
            throw new RuntimeException("Formato inesperado en respuesta Gemini");
        }
        return (String) generated;
    }
}