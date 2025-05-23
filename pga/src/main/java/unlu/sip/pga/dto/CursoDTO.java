package unlu.sip.pga.dto;

import java.util.Date;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CursoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String duracion;
    private String nivel;
    private Date fechaInicio;
    private Float calificacion;
    private Set<ModuloDTO> modulos;
    private Set<CategoriaDTO> categorias;
}
