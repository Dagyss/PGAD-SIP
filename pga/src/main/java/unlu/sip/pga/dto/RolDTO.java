package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class RolDTO {
    private Integer idRol;
    private String nombre;
    private Boolean estado;
    private Set<UsuarioDTO> usuarios;
    private Set<PermisoDTO> permisos;
}
