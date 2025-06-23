package unlu.sip.pga.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 1500)
    private String descripcion;

    @Column(length = 50)
    private String dificultad;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "evaluacion_categoria",
            joinColumns = @JoinColumn(name = "evaluacion_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

    @OneToMany(
            mappedBy = "evaluacion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<TestEvaluacion> evaluacionTests = new HashSet<>();
}
