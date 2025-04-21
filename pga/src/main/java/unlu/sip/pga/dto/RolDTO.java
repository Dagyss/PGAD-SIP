package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RolDTO {
    private Integer idRol;
    private String nombre;
    private String descripcion;
    private String estado;
}
