package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unlu.sip.pga.dto.*;
import unlu.sip.pga.entities.Categoria;
import unlu.sip.pga.entities.Curso;
import unlu.sip.pga.entities.Evaluacion;
import unlu.sip.pga.entities.Modulo;
import unlu.sip.pga.mappers.CursoMapper;
import unlu.sip.pga.mappers.EvaluacionMapper;
import unlu.sip.pga.repositories.CategoriaRepository;
import unlu.sip.pga.repositories.EvaluacionRepository;
import unlu.sip.pga.services.*;

import java.util.*;
import java.util.stream.Collectors;

import unlu.sip.pga.repositories.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
@Service
public class CursoServiceImpl implements CursoService {
    @Autowired private CursoRepository cursoRepository;
    @Autowired private ModuloService moduloService;
    @Autowired private EjercicioService ejercicioService;
    @Autowired private GeminiService gemini;
    @Autowired private CursoMapper cursoMapper;
    @Autowired private EvaluacionService evaluacionService;
    @Autowired private EvaluacionRepository evaluacionRepository;
    @Autowired private CategoriaRepository categoriaRepo;
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    @Transactional
    public CursoDTO crearCurso(Curso curso) throws Exception {
        // 1. Guardar curso base
        Curso cursoGuardado = cursoRepository.save(curso);

        // 2. Generar 5 módulos vía IA
        String prompt = String.format(
                "**IMPORTANTE**: Responde única y exclusivamente con un objeto JSON válido y nada más. " +
                        "El objeto JSON debe tener exactamente dos campos por módulo: \"titulo\" (50 caracteres max) y \"descripcion\" (150 caracteres max). " +
                        "Genera una serie de 3 titulos de módulos de dificultad %s que aumente progresivamente y no se repitan para el curso %s y las categorías %s. y una minima descripcion",
                curso.getNivel(), cursoGuardado.getTitulo(),
                Optional.ofNullable(cursoGuardado.getCategorias())
                        .map(cats -> cats.stream().map(Categoria::getNombre).toList())
                        .orElse(Collections.emptyList())
        );

        String rawResponse = gemini.generarTextoEjercicio(prompt);

        // Saneamiento de la respuesta IA: eliminar backticks, markdown, texto extra
        String trimmed = rawResponse.trim();
        // Si viene envuelto en code fences ```json
        if (trimmed.startsWith("```")) {
            int firstNewline = trimmed.indexOf('\n');
            trimmed = trimmed.substring(firstNewline + 1).trim();
        }
        // Extraer el array JSON de módulos
        int startArr = trimmed.indexOf('[');
        int endArr = trimmed.lastIndexOf(']');
        if (startArr < 0 || endArr < 0 || startArr > endArr) {
            throw new RuntimeException("Respuesta de IA no contiene un array JSON válido: «" + trimmed + "»");
        }
        String jsonArray = trimmed.substring(startArr, endArr + 1);

        JsonNode modulesNode = mapper.readTree(jsonArray);
        // iterar sobre los 3 módulos
        for (int idx = 0; idx < modulesNode.size(); idx++) {
            JsonNode modNode = modulesNode.get(idx);
            ModuloDTO modDto = new ModuloDTO();
            modDto.setCursoId(cursoGuardado.getId());
            modDto.setTitulo(modNode.get("titulo").asText());
            modDto.setDescripcion(modNode.get("descripcion").asText());
            modDto.setOrden(idx + 1);
            modDto.setContenidos(Collections.emptySet());
            modDto.setEjercicios(Collections.emptySet());

            Modulo modulo = moduloService.crearModulo(modDto);

            // 3. Para cada módulo, generar 3 ejercicios sencillos
            for (int j = 1; j <= 3; j++) {
                GenerateEjercicioRequestDTO req = new GenerateEjercicioRequestDTO(
                        modulo.getId(), cursoGuardado.getNivel(),
                        Optional.ofNullable(cursoGuardado.getCategorias())
                                .stream()
                                .flatMap(java.util.Collection::stream)
                                .map(cat -> cat.getId())
                                .toList()
                );
                ejercicioService.generarEjercicio(req);
            }
        }

        GenerateEvaluacionRequestDTO evalReq =
                new GenerateEvaluacionRequestDTO(cursoGuardado.getId(), cursoGuardado.getNivel(),Optional.ofNullable(cursoGuardado.getCategorias())
                        .stream()
                        .flatMap(java.util.Collection::stream)
                        .map(cat -> cat.getId())
                        .toList());
        EvaluacionDTO evalDto = evaluacionService.crearEvaluacion(evalReq);

        Evaluacion evPersistida = evaluacionRepository.findById(evalDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Evaluación no encontrada: " + evalDto.getId()));

        if (cursoGuardado.getEvaluaciones() == null) {
            cursoGuardado.setEvaluaciones(new HashSet<>());
        }
        cursoGuardado.getEvaluaciones().add(evPersistida);

        Set<Categoria> categoriasGestionadas = curso.getCategorias().stream()
                .map(cat -> categoriaRepo.getReferenceById(cat.getId()))
                .collect(Collectors.toSet());
        curso.setCategorias(categoriasGestionadas);

        Curso merged = cursoRepository.save(cursoGuardado);

        return cursoMapper.toDto(merged);
    }






    @Override
    @Transactional(readOnly = true)
    public Optional<CursoDTO> obtenerCursoPorId(Integer id) {
        return cursoRepository.findByIdWithAll(id)
                .map(cursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursos() {
        return cursoRepository.findAllWithAll().stream()
                .map(cursoMapper::toDto)
                .collect(Collectors.toList());
    }



    public Curso actualizarCurso(Curso curso) { return cursoRepository.save(curso); }
    public void eliminarCurso(Integer id) { cursoRepository.deleteById(id); }
}