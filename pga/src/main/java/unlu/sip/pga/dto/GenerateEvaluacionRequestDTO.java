package unlu.sip.pga.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class GenerateEvaluacionRequestDTO {
    private Integer cursoId;
    private String dificultad;
    private List<Integer> categoriaIds;
}