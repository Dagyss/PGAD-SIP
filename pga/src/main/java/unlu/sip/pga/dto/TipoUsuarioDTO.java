package unlu.sip.pga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import unlu.sip.pga.models.enumerados.TipoAcceso;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuarioDTO {
    private String idUsuario;
    private TipoAcceso tipoUsuario; // FREE o PREMIUM

}