package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class ModuloDTO {
    private Integer idModulo;
    private CursoDTO curso;
    private String titulo;
    private String descripcion;
    private Integer orden;
    private Set<ContenidoModuloDTO> contenidos;
    private Set<EjercicioDTO> ejercicios;
}