package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private String id;
    private String nombre;
    private String correo;
    private String nivelConocimiento;
    private SuscripcionDTO tipoUsuario;
    private Boolean estadoCuenta;
}