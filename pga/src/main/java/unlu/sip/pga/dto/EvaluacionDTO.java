package unlu.sip.pga.dto;

import java.util.Date;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EvaluacionDTO {
    private Integer id;
    private Integer idCurso;
    private String titulo;
    private String descripcion;
    private String dificultad;
    private Date fechaCreacion;
    private Set<TestEjercicioDTO> tests;
}