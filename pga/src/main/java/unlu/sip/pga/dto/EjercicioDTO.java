package unlu.sip.pga.dto;

import java.util.Date;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EjercicioDTO {
    private Integer id;
    private ModuloDTO modulo;
    private String titulo;
    private String descripcion;
    private String dificultad;
    private Date fechaCreacion;
    private Set<CategoriaDTO> categorias;
    private Set<TestEjercicioDTO> tests;
}