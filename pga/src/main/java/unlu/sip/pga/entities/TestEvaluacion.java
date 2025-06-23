package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_evaluacion")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestEvaluacion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 1000)
    private String entrada;

    @Column(nullable = false, length = 1000)
    private String salidaEsperada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evaluacion_id", nullable = false)
    private Evaluacion evaluacion;
}