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
        List<String> catNombres = req.getCategoriaIds().stream()
                .map(id -> categoriaRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id))
                        .getNombre()
                )
                .toList();

        // Construir prompt para el modelo
        String prompt = String.format(
                "Eres un generador de ejercicios formativos para una plataforma educativa.\n" +
                "**IMPORTANTE**: Responde **única y exclusivamente** con un objeto JSON válido y nada más.\n" +
                "- El objeto JSON debe tener exactamente tres campos: \"titulo\" (String), \"descripcion\" (String) y \"tests\" (Array de objetos).\n" +
                "- Cada objeto en \"tests\" debe tener dos campos: \"entrada\" (String) y \"salidaEsperada\" (String).\n" +
                "- No incluyas comillas tipográficas, ni símbolos extra, ni texto explicativo.\n" +
                "- Tu respuesta debe ajustarse al siguiente formato de ejemplo:\n" +
                "{\"titulo\":\"Aquí va el título\",\"descripcion\":\"Aquí va la descripción\",\"tests\":[{\"entrada\":\"valor de entrada 1\",\"salidaEsperada\":\"resultado esperado 1\"},{\"entrada\":\"valor de entrada 2\",\"salidaEsperada\":\"resultado esperado 2\"}]}\n\n" +
                "Ahora, genera un ejercicio de dificultad %s para el módulo %d y las categorías %s.\n" +
                "Límite: máximo 1000 caracteres en el campo \"descripcion\".\n",
                req.getDificultad(),
                req.getModuloId(),
                catNombres
        );


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
                .modulo(mod)
                .dificultad(req.getDificultad())
                .titulo(titulo)
                .descripcion(descripcion)
                .fechaCreacion(new Date())
                .categorias(cats)
                .build();

        // Guardar y mapear
        Ejercicio saved = ejercicioRepository.save(entity);

        Set<TestEjercicio> testEntities = testDTOs.stream()
        .map(dto -> TestEjercicio.builder()
                .entrada(dto.getEntrada())
                .esperado(dto.getSalidaEsperada())
                .ejercicio(entity)
                .build())
        .collect(Collectors.toSet());

        // Asociarlos al ejercicio
        entity.setTests(testEntities);

        // Guardar la relación completa
        ejercicioRepository.save(entity);

        return mapper.toDto(saved);
    }
    public Optional<Ejercicio> obtenerEjercicioPorId(Integer id) { return ejercicioRepository.findById(id); }
    public List<Ejercicio> listarEjerciciosPorModulo(Integer moduloId) { return ejercicioRepository.findByModuloId(moduloId); }
    public Ejercicio actualizarEjercicio(Ejercicio ejercicio) { return ejercicioRepository.save(ejercicio); }
    public void eliminarEjercicio(Integer id) { ejercicioRepository.deleteById(id); }
}