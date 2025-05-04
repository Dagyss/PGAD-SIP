package unlu.sip.pga.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SuscripcionDTO {
    private Integer id;
    private UsuarioDTO usuario;
    private TipoSuscripcionDTO suscripcion;
    private Date fechaInicio;
    private Date fechaFin;
    
}
