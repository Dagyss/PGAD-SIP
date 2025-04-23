package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProgresoDTO {
    private Integer idProgreso;
    private Integer cursoId;
    private Integer idUsuario;
    private Float porcentajeCompletado;
    private Date fechaActualizacion;
}