package unlu.sip.pga.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CertificacionDTO {
    private Integer id;
    private UsuarioDTO usuario;
    private CursoDTO curso;
    private Date fechaEmision;
}