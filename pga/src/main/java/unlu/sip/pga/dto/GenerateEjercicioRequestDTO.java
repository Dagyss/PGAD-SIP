package unlu.sip.pga.dto;

import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateEjercicioRequestDTO {
    private Integer moduloId;
    private Integer dificultad;
    private Set<Integer> categoriaIds;
}
