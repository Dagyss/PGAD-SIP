package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ModuloDTO {
    private Integer idModulo;
    private Integer cursoId;
    private String titulo;
    private String descripcion;
    private Integer orden;
}