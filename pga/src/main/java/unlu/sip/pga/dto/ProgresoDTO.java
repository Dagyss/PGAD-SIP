package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProgresoDTO {
    private Integer idProgreso;
    private CursoDTO curso;
    private UsuarioDTO Usuario;
    private Float porcentajeCompletado;
    private Date fechaActualizacion;
}