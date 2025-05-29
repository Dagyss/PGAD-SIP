package unlu.sip.pga.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/corregir")
public class CorregirEjerciciosController {
    @Autowired
    private StringRedisTemplate redis;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    public String enviarTarea(@RequestBody TestRequest request) throws Exception {
        String taskId = UUID.randomUUID().toString();

        // Armar mensaje JSON
        String mensaje = mapper.writeValueAsString(request);

        // Enviar a RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq"); // o localhost si estás probando local
        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare("python_tests", false, false, false, null);
            channel.basicPublish("", "python_tests", null, mensaje.getBytes());
        }

        // Esperar resultado en Redis (máx. 5 segundos)
        for (int i = 0; i < 50; i++) {
            String resultado = redis.opsForValue().get(taskId);
            if (resultado != null && !resultado.equals("running")) {
                return resultado;
            }
            Thread.sleep(100); // Esperar 100ms
        }

        return "{\"success\":false,\"error\":\"Timeout esperando resultado\"}";
    }
}
