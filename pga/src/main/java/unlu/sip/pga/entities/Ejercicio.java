
package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ejercicio")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idModulo", nullable = false)
    private Modulo modulo;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @Column(length = 50)
    private String dificultad;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "ejercicio_categoria",
            joinColumns = @JoinColumn(name = "ejercicio_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;
}
