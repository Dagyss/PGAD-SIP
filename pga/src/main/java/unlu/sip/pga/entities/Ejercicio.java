package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ejercicio")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idModulo", nullable = false)
    private Modulo modulo;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 200)
    private String descripcion;

    @Column
    private Integer dificultad;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;


}
