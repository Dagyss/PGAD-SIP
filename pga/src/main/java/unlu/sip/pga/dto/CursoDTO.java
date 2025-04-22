package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CursoDTO {
    private Integer idCurso;
    private String titulo;
    private String descripcion;
    private Float duracion;
    private String nivel;
    private Date fechaInicio;
    private Float calificacion;
}
