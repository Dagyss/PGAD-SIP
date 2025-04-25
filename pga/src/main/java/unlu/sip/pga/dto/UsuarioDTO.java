package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import unlu.sip.pga.entities.Rol;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String password;
    private String nivelConocimiento;
    private String tipoUsuario;
    private Boolean estadoCuenta;
    private Set<RolDTO> roles;
}