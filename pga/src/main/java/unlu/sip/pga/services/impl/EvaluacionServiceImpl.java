package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import unlu.sip.pga.dto.*;
import unlu.sip.pga.entities.Ejercicio;
import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.entities.TestEjercicio;
import unlu.sip.pga.mappers.*;
import unlu.sip.pga.repositories.CursoRepository;
import unlu.sip.pga.services.*;
import unlu.sip.pga.repositories.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluacionServiceImpl implements EvaluacionService {
    @Autowired private EvaluacionRepository evaluacionRepository;
    @Autowired private ModuloService moduloService;
    @Autowired private EjercicioService ejercicioService;
    @Autowired private GeminiService gemini;
    @Autowired private CursoRepository cursoRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public EvaluacionDTO crearEvaluacion(GenerateEvaluacionRequestDTO req) throws Exception {
        // 1. Recuperar módulos del curso
        List<Modulo> modDtos = moduloService.listarModulosPorCurso(req.getCursoId());
        List<String> titulosModulos = modDtos.stream()
                .map(m -> m.getTitulo().trim().toLowerCase())
                .toList();

        // 2. Recuperar ejercicios de cada módulo
        StringBuilder sbEjercicios = new StringBuilder();
        for (Modulo m : modDtos) {
            List<Ejercicio> ejDtos = ejercicioService.listarEjerciciosPorModulo(m.getId());
            if (!ejDtos.isEmpty()) {
                sbEjercicios.append("Ejercicios en módulo \"")
                        .append(m.getTitulo()).append("\":\n");
                for (Ejercicio e : ejDtos) {
                    sbEjercicios.append("  - ").append(e.getTitulo()).append("\n");
                }
                sbEjercicios.append("\n");
            }
        }
        if (sbEjercicios.isEmpty()) {
            sbEjercicios.append("No hay ejercicios previos en este curso.\n\n");
        }

        // 3. Construir prompt
        String prompt = String.format(
                "Eres un generador de evaluaciones integradoras para una plataforma educativa.\n" +
                        "**IMPORTANTE**: Responde única y exclusivamente con un objeto JSON válido y nada más.\n" +
                        "El objeto JSON debe contener:\n" +
                        "  - \"titulo\" (String)\n" +
                        "  - \"descripcion\" (String)\n" +
                        "  - \"tests\" (Array de objetos con {\"entrada\":String, \"salidaEsperada\":String}).\n" +
                        "Cada test debe tener entre 3 y 5 casos.\n\n" +
                        "Títulos de módulos ya existentes:\n%s\n" +
                        "Títulos de ejercicios ya existentes:\n%s\n" +
                        "Ahora, genera UNA sola evaluación integradora de dificultad \"%s\" para el curso con ID %d.\n" +
                        "Asegúrate de que el título y la descripción **no coincidan ni sean muy parecidos**\n" +
                        "a ninguno de los ya listados. Máximo 1500 caracteres en descripción.\n",
                // inserción de títulos de módulos
                titulosModulos.stream()
                        .map(t -> "- " + t).collect(Collectors.joining("\n")),
                // inserción de títulos de ejercicios
                sbEjercicios.toString(),
                req.getDificultad(),
                req.getCursoId()
        );

        // 4. Llamada a la IA
        String raw = gemini.generarTextoEjercicio(prompt).trim();
        // Limpiar posibles code fences
        if (raw.startsWith("```")) {
            raw = raw.substring(raw.indexOf('\n') + 1, raw.lastIndexOf("```")).trim();
        }
        // Extraer objeto JSON
        int start = raw.indexOf('{'), end = raw.lastIndexOf('}');
        if (start < 0 || end < 0) {
            throw new RuntimeException("Respuesta IA sin JSON válido:\n" + raw);
        }
        String json = raw.substring(start, end+1);

        // 5. Parsear y guardar
        JsonNode node = mapper.readTree(json);
        Evaluacion ev = new Evaluacion();
        ev.setCurso(cursoRepository.getReferenceById(req.getCursoId()));
        ev.setTitulo(node.get("titulo").asText());
        ev.setDescripcion(node.get("descripcion").asText());
        ev.setDificultad(req.getDificultad());
        ev.setFechaCreacion(new Date());

        // Tests
        List<TestEjercicioDTO> tests = mapper.readValue(
                node.get("tests").toString(),
                new TypeReference<List<TestEjercicioDTO>>() {}
        );
        Set<TestEjercicio> testEntities = tests.stream()
                .map(dto -> {
                    TestEjercicio test = TestEjercicio.builder()
                            .entrada(dto.getEntrada())
                            .salidaEsperada(dto.getSalidaEsperada())
                            .build();
                    test.setEjercicio(ev);
                    return test;
                }).collect(Collectors.toSet());
        ev.setTests(testEntities);

        Evaluacion saved = evaluacionRepository.save(ev);

        return EvaluacionMapper.INSTANCE.toDto(saved);
    }

    public Optional<Evaluacion> obtenerEvaluacionPorId(Integer idEvaluacion) { return evaluacionRepository.findById(idEvaluacion); }
    public List<Evaluacion> listarEvaluacionesPorCurso(Integer idCurso) { return evaluacionRepository.findByCursoId(idCurso); }
    public Evaluacion actualizarEvaluacion(Evaluacion ev) { return evaluacionRepository.save(ev); }
    public void eliminarEvaluacion(Integer id) { evaluacionRepository.deleteById(id); }
}