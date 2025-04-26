package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "modulo")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 200)
    private String descripcion;

    @Column
    private Integer orden;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
    private Set<ContenidoModulo> contenidos;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
    private Set<Ejercicio> ejercicios;
}