package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "test_ejercicio")
public class TestEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id", nullable = false)
    private Ejercicio ejercicio;

    @Column(nullable = false, length = 200)
    @EqualsAndHashCode.Include
    private String entrada;

    @Column(nullable = false, length = 200)
    @EqualsAndHashCode.Include
    private String salidaEsperada;
}
