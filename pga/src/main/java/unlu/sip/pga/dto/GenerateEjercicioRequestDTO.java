// 1. DTO para petición de generación
package unlu.sip.pga.dto;

import java.util.Date;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateEjercicioRequestDTO {
    private Integer moduloId;
    private Integer dificultad;
    private List<Integer> categoriaIds;
}

