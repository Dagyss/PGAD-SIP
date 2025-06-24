package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TipoSuscripcionDTO {
    private Integer id;
    private BigDecimal precio;
    private String tipoSuscripcion;
}