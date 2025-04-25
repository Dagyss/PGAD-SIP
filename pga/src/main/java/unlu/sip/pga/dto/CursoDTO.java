package unlu.sip.pga.dto;

import java.util.Date;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import unlu.sip.pga.entities.Modulo;

@Data @NoArgsConstructor @AllArgsConstructor
public class CursoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Float duracion;
    private String nivel;
    private Date fechaInicio;
    private Float calificacion;
    private Set<ModuloDTO> modulos;
}
