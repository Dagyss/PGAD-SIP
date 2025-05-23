package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final LlamaService llama;
    private final EjercicioMapper mapper;
    
    @Override
    @Transactional
    public EjercicioDTO generarEjercicio(GenerateEjercicioRequestDTO req) throws Exception {
        // Construir prompt para el modelo
        String prompt = String.format(
                "Genera un ejercicio de dificultad %d para el módulo %d y categorías %s.\n" +
                        "Limitandote a un maximo de 1000 caracteres.\n",
                req.getDificultad(),
                req.getModuloId(),
                req.getCategoriaIds().toString()
        );

        String respuesta = llama.generarTextoEjercicio(prompt);

        // Parsear JSON de respuesta
        ObjectMapper om = new ObjectMapper();
        JsonNode node = om.readTree(respuesta);
        String titulo = node.get("titulo").asText();
        String descripcion = node.get("descripcion").asText();

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
        return mapper.toDto(saved);
    }
    public Optional<Ejercicio> obtenerEjercicioPorId(Integer id) { return ejercicioRepository.findById(id); }
    public List<Ejercicio> listarEjerciciosPorModulo(Integer moduloId) { return ejercicioRepository.findByModuloId(moduloId); }
    public Ejercicio actualizarEjercicio(Ejercicio ejercicio) { return ejercicioRepository.save(ejercicio); }
    public void eliminarEjercicio(Integer id) { ejercicioRepository.deleteById(id); }
}