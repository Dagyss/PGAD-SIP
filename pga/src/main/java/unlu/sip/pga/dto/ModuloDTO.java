package unlu.sip.pga.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class ModuloDTO {
    private Integer id;
    private Integer cursoId;
    private String titulo;
    private String descripcion;
    private Integer orden;
    @JsonIgnore
    private Set<ContenidoModuloDTO> contenidos;
    @JsonIgnore
    private Set<EjercicioDTO> ejercicios;
}