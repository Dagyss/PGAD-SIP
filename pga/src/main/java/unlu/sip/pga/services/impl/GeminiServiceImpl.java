package unlu.sip.pga.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import java.util.List;
import unlu.sip.pga.services.LlamaService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiServiceImpl implements LlamaService {
    private final RestTemplate rest = new RestTemplate();

    @Override
    public String generarTextoEjercicio(String prompt) throws Exception {
        Client client = new Client();
        GenerateContentResponse response =
                client.models.generateContent("gemini-2.0-flash-001", prompt, null);
        return response.text();
    }
}