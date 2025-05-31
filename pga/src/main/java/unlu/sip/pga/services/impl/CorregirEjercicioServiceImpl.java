package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import unlu.sip.pga.dto.CodigoUsuarioDTO;
import unlu.sip.pga.entities.Ejercicio;
import unlu.sip.pga.services.CorregirEjercicioService;
import unlu.sip.pga.services.EjercicioService;

import java.util.UUID;

@Service
public class CorregirEjercicioServiceImpl implements CorregirEjercicioService {

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private EjercicioService ejercicioService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String enviarTarea(CodigoUsuarioDTO request) throws Exception {
        String taskId = UUID.randomUUID().toString();

        Ejercicio ejercicio = ejercicioService.obtenerEjercicioPorId(request.getIdEjercicio())
                .orElseThrow(() -> new IllegalArgumentException("Ejercicio no encontrado"));

        String lenguaje = ejercicio.getCategorias().stream()
                .map(c -> c.getNombre().toLowerCase())
                .findFirst()
                .orElse("python");

        String queue = lenguaje.equals("java") ? "java_tests" : "python_tests";

        // Armar mensaje con código y taskId
        var payload = mapper.writeValueAsString(new Mensaje(request.getCodigo(), request.getIdEjercicio(), taskId));

        // Enviar a RabbitMQ
        try (Connection conn = new ConnectionFactory() {{ setHost("rabbitmq"); }}.newConnection();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, payload.getBytes());
        }

        // Esperar resultado en Redis
        for (int i = 0; i < 50; i++) {
            String result = redis.opsForValue().get(taskId);
            if (result != null && !result.equalsIgnoreCase("running")) {
                return result;
            }
            Thread.sleep(100);
        }

        return "{\"success\":false,\"error\":\"Timeout esperando resultado\"}";
    }

    // DTO interno simple
    static class Mensaje {
        public String codigo;
        public Integer ejercicioId;
        public String taskId;

        public Mensaje(String codigo, Integer ejercicioId, String taskId) {
            this.codigo = codigo;
            this.ejercicioId = ejercicioId;
            this.taskId = taskId;
        }
    }
}
