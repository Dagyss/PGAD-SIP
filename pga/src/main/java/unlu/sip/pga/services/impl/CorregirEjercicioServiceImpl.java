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

        Integer idEjercicio = request.getIdEjercicio();
        if (idEjercicio == null || idEjercicio <= 0) {
            throw new IllegalArgumentException("ID de ejercicio inválido");
        }

        // Traer de la bd los tests bajo el id de ejercicio
        String testsJson = ejercicioService.obtenerTestsPorEjercicioId(idEjercicio);

        Ejercicio ejercicio = ejercicioService.obtenerEjercicioPorId(idEjercicio)
                .orElseThrow(() -> new IllegalArgumentException("Ejercicio no encontrado con ID: " + idEjercicio));

        String categoria = ejercicio.getCategorias().stream()
                .map(c -> c.getNombre())
                .findFirst()
                .orElse("Sin categoría");

        String mensaje = request.getCodigo();
        String qName = "python_tests";
        if (!"python".equalsIgnoreCase(categoria)) {
            qName = "java_tests";
        }

        // Enviar a RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare(qName, false, false, false, null);
            channel.basicPublish("", qName, null, mensaje.getBytes());
        }

        // Esperar resultado en Redis
        for (int i = 0; i < 50; i++) {
            String resultado = redis.opsForValue().get(taskId);
            if (resultado != null && !resultado.equals("running")) {
                return resultado;
            }
            Thread.sleep(100);
        }

        return "{\"success\":false,\"error\":\"Timeout esperando resultado\"}";
    }
}
