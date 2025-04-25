package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ContenidoModuloDTO {
    private Integer id;
    private ModuloDTO modulo;
    private String tipo;
    private String recurso;
}
