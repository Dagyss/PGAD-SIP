package unlu.sip.pga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionDTO {
    private Integer idCurso;
    private String idUsuario;
    private Integer calificacion;
    private String comentario;

}