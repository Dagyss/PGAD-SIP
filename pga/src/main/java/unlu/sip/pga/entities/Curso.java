package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 200)
    private String descripcion;

    @Column(length = 50)
    private String duracion;

    @Column(length = 15)
    private String nivel;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column
    private Float calificacion;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Modulo> modulos;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "curso_categoria",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;
}