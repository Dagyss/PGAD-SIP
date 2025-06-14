package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import unlu.sip.pga.dto.CodigoUsuarioDTO;
import unlu.sip.pga.dto.CodigoUsuarioEvaluacionDTO;
import unlu.sip.pga.entities.Ejercicio;
import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.services.*;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.Map;

@Service
public class CorregirEvaluacionServiceImpl implements CorregirEvaluacionService {
    @Autowired private GeminiService gemini;
    @Autowired
    private EvaluacionService evaluacionService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String enviarTarea(CodigoUsuarioEvaluacionDTO request) throws Exception {
        String taskId = UUID.randomUUID().toString();
        System.out.println("[1] Generado taskId: " + taskId);

        Evaluacion evaluacion;
        try {
            evaluacion = evaluacionService.obtenerEvaluacionPorId(request.getIdEvaluacion())
                    .orElseThrow(() -> new IllegalArgumentException("Evaluacion no encontrada"));
            System.out.println("[2] Evaluacion obtenido: " + evaluacion.getTitulo());
        } catch (Exception e) {
            System.err.println("[ERROR] No se pudo obtener el evaluacion: " + e.getMessage());
            throw e;
        }

        String lenguaje = evaluacion.getCategorias().stream()
                .map(c -> c.getNombre().toLowerCase())
                .findFirst()
                .orElse("python");
        System.out.println("[3] Lenguaje detectado: " + lenguaje);

        String queue = lenguaje.equals("java") ? "java_tests" : "python_tests";
        System.out.println("[4] Cola a usar: " + queue);

        // Conseguir los tests de la evaluacion
        String tests = evaluacionService.obtenerTestsPorEvaluacionId(request.getIdEvaluacion());
        System.out.println("[4.1] Tests obtenidos: " + tests);

        String payload = mapper.writeValueAsString(Map.of(
                "codigo", request.getCodigo(),
                "task-id", taskId,
                "tests", tests
        ));

        System.out.println("[5] Payload generado: " + payload);

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("rabbitmq");
            try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel()) {
                channel.queueDeclare(queue, false, false, false, null);
                channel.basicPublish("", queue, null, payload.getBytes());
                System.out.println("[6] Mensaje enviado a RabbitMQ.");
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Fallo al conectar o enviar mensaje a RabbitMQ: " + e.getMessage());
            throw e;
        }

        System.out.println("[7] Esperando resultado en Redis con taskId: " + taskId);
        try (Jedis jedis = new Jedis("redis", 6379)) {
            for (int i = 0; i < 50; i++) {
                String result = jedis.get(taskId);
                System.out.println("[7." + i + "] Resultado obtenido: " + result);
                if (result != null && !result.equalsIgnoreCase("running")) {
                    result = gemini.generarTextoEjercicio(String.format(
                            "Eres un corrector de ejercicios formativos para una plataforma educativa." +
                                    "**IMPORTANTE**: Responde única y exclusivamente con un String válido y nada más. "+
                                    "Indica cuales son los errores y como mejorar el siguiente fragmento de codigo: %s"+
                                    ", que provoca este error %s y que busca resolver la siguiente consigna %s",request.getCodigo()
                            , result,evaluacion.getDescripcion()));
                    return result;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] No se pudo conectar o leer de Redis: " + e.getMessage());
            return "{\"success\":false,\"error\":\"Error de conexión a Redis: " + e.getMessage() + "\"}";
        }

        System.out.println("[8] Timeout esperando resultado de Redis.");
        return "{\"success\":false,\"error\":\"Timeout esperando resultado\"}";
    }

    static class Mensaje {
        public String codigo;
        public Integer evaluacionId;
        public String taskId;

        public Mensaje(String codigo, Integer evaluacionId, String taskId) {
            this.codigo = codigo;
            this.evaluacionId = evaluacionId;
            this.taskId = taskId;
        }
    }
}
