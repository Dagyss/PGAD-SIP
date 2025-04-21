package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SuscripcionDTO {
    private Integer idSuscripcion;
    private Integer usuarioId;
    private String tipoSuscripcion;
    private Date fechaInicio;
    private Date fechaFin;
}