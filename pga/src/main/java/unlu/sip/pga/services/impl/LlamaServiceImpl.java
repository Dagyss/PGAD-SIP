package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import unlu.sip.pga.services.LlamaService;

@Service
public class LlamaServiceImpl implements LlamaService {
    private final RestTemplate rest = new RestTemplate();
    private final String llamaUrl = "http://localhost:11434/completion";

    @Override
    public String generarTextoEjercicio(String prompt) throws Exception {
        Map<String,Object> body = Map.of(
                "prompt", prompt,
                "n_predict", 128
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> resp = rest.postForEntity(llamaUrl, request, Map.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error llamando a Llama: " + resp.getStatusCode());
        }
        Map<String, Object> json = resp.getBody();
        return (String) json.get("content");
    }
}
