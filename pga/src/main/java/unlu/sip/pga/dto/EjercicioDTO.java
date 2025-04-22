package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EjercicioDTO {
    private Integer idEjercicio;
    private Integer moduloId;
    private String titulo;
    private String descripcion;
    private Integer dificultad;
    private Date fechaCreacion;
}