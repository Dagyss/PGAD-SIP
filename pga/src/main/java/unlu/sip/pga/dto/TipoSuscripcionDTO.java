package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TipoSuscripcionDTO {
    private Integer id;
    private float precio;
    private String tipoSuscripcion;
}