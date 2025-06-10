package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import unlu.sip.pga.services.GeminiService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiServiceImpl implements GeminiService {
    private final RestTemplate rest = new RestTemplate();

    @Override
    public String generarTextoEjercicio(String prompt) throws Exception {
        Client client = new Client();
        GenerateContentResponse response =
                client.models.generateContent("gemini-2.0-flash-001", prompt, null);
        return response.text();
    }
}