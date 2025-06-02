package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import unlu.sip.pga.dto.*;
import unlu.sip.pga.entities.*;
import unlu.sip.pga.mappers.EjercicioMapper;
import unlu.sip.pga.repositories.*;
import unlu.sip.pga.services.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EjercicioServiceImpl implements EjercicioService {
    private final EjercicioRepository ejercicioRepository;
    private final ModuloRepository moduloRepo;
    private final CategoriaRepository categoriaRepo;
    private final GeminiService gemini;
    private final EjercicioMapper mapper;
    
    @Override
    @Transactional
    public EjercicioDTO generarEjercicio(GenerateEjercicioRequestDTO req) throws Exception {
        Integer moduloId = req.getModuloId();
        String dificultad = req.getDificultad();

        List<Ejercicio> ejerciciosExistentes = ejercicioRepository.findByModuloId(moduloId);
        Set<String> titulosExistentes = ejerciciosExistentes.stream()
                .map(e -> e.getTitulo().trim().toLowerCase())
                .collect(Collectors.toSet());

        List<String> catNombres = req.getCategoriaIds().stream()
                .map(id -> categoriaRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id))
                        .getNombre()
                )
                .toList();

        String moduloTitulo = moduloRepo.findById(moduloId)
                .orElseThrow(() -> new IllegalArgumentException("Módulo no encontrado"))
                .getTitulo();

        StringBuilder sbTitulos = new StringBuilder();
        if (!titulosExistentes.isEmpty()) {
            sbTitulos.append("Títulos de ejercicios ya existentes en este módulo:\n");
            int idx = 1;
            for (String t : titulosExistentes) {
                sbTitulos.append(String.format("%d) \"%s\"\n", idx++, t));
            }
            sbTitulos.append("\n");
        } else {
            sbTitulos.append("No hay ejercicios previos en este módulo.\n\n");
        }

        // 5) Construir el prompt completo
        String prompt = String.format(
                "Eres un generador de ejercicios formativos para una plataforma educativa.\n" +
                        "**IMPORTANTE**: Responde **única y exclusivamente** con un objeto JSON válido y nada más. " +
                        "El objeto JSON debe tener exactamente estos campos:\n" +
                        "  - \"titulo\" (String)\n" +
                        "  - \"descripcion\" (String)\n" +
                        "  - \"tests\" (Array de objetos con {\"entrada\":String, \"salidaEsperada\":String}).\n" +
                        "Cada objeto en \"tests\" debe:\n" +
                        "  • Tener entre 3 y 5 casos de prueba.\n" +
                        "  • Si la función espera múltiplos números como entrada, sepáralos con comas (ej: \"3,5\" o \"10,-2,7\").\n" +
                        "  • Si la entrada es un arreglo (lista), exprésalo como array válido de Python (ej: \"[1, 2, 3]\").\n" +
                        "No incluyas comillas tipográficas ni texto explicativo adicional.\n" +
                        "\n" +
                        // Aquí insertamos la lista (o aviso) de títulos existentes:
                        sbTitulos.toString() +
                        "\n" +
                        "Ahora, genera UN SOLO ejercicio de dificultad \"%s\" para el módulo \"%s\" y las categorías %s. " +
                        "Asegúrate de que el título y la descripción de este ejercicio **no coincidan ni sean muy parecidos** " +
                        "a ninguno de los que ya existen. Límite: 1000 caracteres en el campo \"descripcion\".\n",
                dificultad,
                moduloTitulo,
                catNombres
        );

        System.out.println("Prompt enviado: «" + prompt + "»");

        String respuestaRaw = gemini.generarTextoEjercicio(prompt);

        System.out.println("RAW IA response: «" + respuestaRaw + "»");

        // Saneamiento

        String raw = respuestaRaw.trim();
        int start = raw.indexOf('{');
        int end   = raw.lastIndexOf('}');
        if (start < 0 || end < 0 || start > end) {
            throw new RuntimeException("Respuesta de IA no contiene un objeto JSON válido: «" + raw + "»");
        }
        String json = raw.substring(start, end + 1);

        // Parsear JSON de respuesta
        JsonNode node = new ObjectMapper().readTree(json);
        String titulo = node.get("titulo").asText();
        String descripcion = node.get("descripcion").asText();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TestEjercicioDTO> testDTOs = objectMapper.readValue(
                node.get("tests").toString(),
                new TypeReference<List<TestEjercicioDTO>>() {}
        );
        // Obtener entidad Modulo
        Modulo mod = moduloRepo.findById(req.getModuloId())
                .orElseThrow(() -> new IllegalArgumentException("Módulo no encontrado"));

        // Obtener categorías
        Set<Categoria> cats = req.getCategoriaIds().stream()
                .map(id -> categoriaRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id)))
                .collect(Collectors.toSet());

        // Construir entidad Ejercicio
        Ejercicio entity = Ejercicio.builder()
                .modulo(moduloRepo.getReferenceById(req.getModuloId()))
                .dificultad(req.getDificultad())
                .titulo(titulo)
                .descripcion(descripcion)
                .fechaCreacion(new Date())
                .categorias(cats)
                .build();

        // Crear tests
        Set<TestEjercicio> testEntities = testDTOs.stream()
        .map(dto -> {
                TestEjercicio test = TestEjercicio.builder()
                .entrada(dto.getEntrada())
                .salidaEsperada(dto.getSalidaEsperada())
                .build();
                test.setEjercicio(entity); // aseguramos que apunte al ejercicio
                return test;
        })
        .collect(Collectors.toSet());

        // Asociar los tests al ejercicio
        entity.setTests(testEntities);

        System.out.println("Cantidad de tests a guardar: " + entity.getTests().size());
        entity.getTests().forEach(t -> System.out.println(t.getEntrada() + " -> " + t.getSalidaEsperada()));


        // Guardar ejercicio con los tests ya asociados
        Ejercicio saved = ejercicioRepository.save(entity);

        // Mapear a DTO
        return mapper.toDto(saved);

    }
    public Optional<Ejercicio> obtenerEjercicioPorId(Integer id) { return ejercicioRepository.findById(id); }
    public List<Ejercicio> listarEjerciciosPorModulo(Integer moduloId) { return ejercicioRepository.findByModuloId(moduloId); }
    public Ejercicio actualizarEjercicio(Ejercicio ejercicio) { return ejercicioRepository.save(ejercicio); }
    public void eliminarEjercicio(Integer id) { ejercicioRepository.deleteById(id); }
    public String obtenerTestsPorEjercicioId(Integer idEjercicio) throws Exception {
        Ejercicio ejercicio = ejercicioRepository.findById(idEjercicio)
                .orElseThrow(() -> new IllegalArgumentException("Ejercicio no encontrado con ID: " + idEjercicio));

        Set<TestEjercicio> tests = ejercicio.getTests();
        if (tests.isEmpty()) {
                throw new IllegalArgumentException("No hay tests asociados al ejercicio con ID: " + idEjercicio);
        }

        // Convertir a DTOs planos
        List<TestEjercicioDTO> testDtos = tests.stream()
                .map(TestEjercicioDTO::new)
                .toList();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(testDtos);
        }
}