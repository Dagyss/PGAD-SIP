package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EvaluacionDTO {
    private Integer idEvaluacion;
    private CursoDTO curso;
    private UsuarioDTO Usuario;
    private Date fecha;
    private Float puntaje;
}