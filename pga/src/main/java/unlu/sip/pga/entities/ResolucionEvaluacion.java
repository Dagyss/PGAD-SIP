package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.*;
import unlu.sip.pga.models.enumerados.EstadoResolucion;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "resolucion_evaluacion")
public class ResolucionEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEvaluacion", nullable = false)
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fechaResolucion", nullable = false)
    private LocalDate fechaResolucion;

    @Lob
    @Column(nullable = false)
    private String resolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoResolucion estado;  //CORRECTO, INCORRECTO
}
