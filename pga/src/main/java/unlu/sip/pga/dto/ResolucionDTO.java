package unlu.sip.pga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolucionDTO {
    private Integer id;
    private Integer idEjercicio;
    private Integer idUsuario;
    private LocalDate fechaResolucion;
    private String resolucion;
    private String estado;

}
