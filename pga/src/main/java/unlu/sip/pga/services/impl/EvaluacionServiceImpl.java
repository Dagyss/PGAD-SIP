package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import unlu.sip.pga.dto.*;
import unlu.sip.pga.entities.*;
import unlu.sip.pga.mappers.*;
import unlu.sip.pga.repositories.CategoriaRepository;
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
    @Autowired private EvaluacionMapper evaluacionMapper;
    @Autowired private CategoriaRepository categoriaRepo;
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
                        "Cada objeto en \"tests\" debe:\n" +
                        "  • Tener entre 3 y 5 casos de prueba.\n" +
                        "  • Si la función espera múltiplos números como entrada, sepáralos con comas (ej: \"3,5\" o \"10,-2,7\").\n" +
                        "  • Si la entrada es un arreglo (lista), exprésalo como array válido de Python (ej: \"[1, 2, 3]\").\n" +
                        "Títulos de módulos ya existentes:\n%s\n" +
                        "Títulos de ejercicios ya existentes:\n%s\n" +
                        "Ahora, genera UN solo ejercicio integrador de dificultad \"%s\" para el curso con ID %d.\n" +
                        "Asegúrate de que el ejercicio en cuestion este relacionado con los anteriores, pero **no coincida ni sea muy parecido**\n" +
                        "a ninguno de los ya listados. Máximo 1500 caracteres en descripción.\n",
                // inserción de títulos de módulos
                titulosModulos.stream()
                        .map(t -> "- " + t).collect(Collectors.joining("\n")),
                // inserción de títulos de ejercicios
                sbEjercicios.toString(),
                req.getDificultad(),
                req.getCursoId()
        );
        System.out.println("Prompt enviado: «" + prompt + "»");
        // 4. Llamada a la IA
        String respuestaRaw = gemini.generarTextoEjercicio(prompt).trim();
        System.out.println("RAW IA response: «" + respuestaRaw + "»");

        // Limpiar posibles code fences
        String raw = respuestaRaw.trim();
        int start = raw.indexOf('{');
        int end   = raw.lastIndexOf('}');
        if (start < 0 || end < 0 || start > end) {
            throw new RuntimeException("Respuesta de IA no contiene un objeto JSON válido: «" + raw + "»");
        }
        String json = raw.substring(start, end + 1);


        Set<Categoria> cats = req.getCategoriaIds().stream()
                .map(id -> categoriaRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id)))
                .collect(Collectors.toSet());

        // 5. Parsear y guardar
        JsonNode node = mapper.readTree(json);
        Evaluacion ev = Evaluacion.builder()
                .curso(cursoRepository.getReferenceById(req.getCursoId()))
                .titulo(node.get("titulo").asText())
                .descripcion(node.get("descripcion").asText())
                .fechaCreacion(new Date())
                .dificultad(req.getDificultad())
                .categorias(cats)
                .build();

        // Tests
        List<TestEjercicioDTO> tests = mapper.readValue(
                node.get("tests").toString(),
                new TypeReference<List<TestEjercicioDTO>>() {}
        );
        Set<TestEvaluacion> testEntities = tests.stream()
                .map(dto -> {
                    TestEvaluacion test = TestEvaluacion.builder()
                            .entrada(dto.getEntrada())
                            .salidaEsperada(dto.getSalidaEsperada())
                            .build();
                    test.setEvaluacion(ev); // aseguramos que apunte a la evaluacion
                    return test;
                })
                .collect(Collectors.toSet());
        ev.setEvaluacionTests(testEntities);


        System.out.println("Cantidad de tests a guardar: " + ev.getEvaluacionTests().size());
        ev.getEvaluacionTests().forEach(t -> System.out.println(t.getEntrada() + " -> " + t.getSalidaEsperada()));

        Evaluacion saved = evaluacionRepository.save(ev);

        return evaluacionMapper.toDto(saved);
    }

    public Optional<Evaluacion> obtenerEvaluacionPorId(Integer idEvaluacion) { return evaluacionRepository.findById(idEvaluacion); }
    public List<Evaluacion> listarEvaluacionesPorCurso(Integer idCurso) { return evaluacionRepository.findByCursoIdWithTests(idCurso); }
    public Evaluacion actualizarEvaluacion(Evaluacion ev) { return evaluacionRepository.save(ev); }
    public void eliminarEvaluacion(Integer id) { evaluacionRepository.deleteById(id); }
    public String obtenerTestsPorEvaluacionId(Integer idEvaluacion) throws Exception {
        Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion)
                .orElseThrow(() -> new IllegalArgumentException("Evaluacion no encontrada con ID: " + idEvaluacion));

        Set<TestEvaluacion> tests = evaluacion.getEvaluacionTests();
        if (tests.isEmpty()) {
            throw new IllegalArgumentException("No hay tests asociados a la evaluacion con ID: " + idEvaluacion);
        }

        // Convertir a DTOs planos
        List<TestEjercicioDTO> testDtos = tests.stream()
                .map(TestEjercicioDTO::new)
                .toList();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(testDtos);
    }
}