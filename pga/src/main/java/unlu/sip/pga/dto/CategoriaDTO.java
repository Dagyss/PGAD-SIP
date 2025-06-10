package unlu.sip.pga.dto;

import lombok.*;
import unlu.sip.pga.models.enumerados.TipoCategoria;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Integer id;
    private String nombre;
    private TipoCategoria tipo;  // LENGUAJE o TEMA
}
