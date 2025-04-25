package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import unlu.sip.pga.entities.Rol;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class PermisoDTO {
    private Integer idPermiso;
    private String nombre;
    private Set<RolDTO> roles;
}