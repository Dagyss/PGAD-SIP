package unlu.sip.pga.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionUsuarioDTO {
    private Integer idUsuario;
    private Integer idSuscripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}