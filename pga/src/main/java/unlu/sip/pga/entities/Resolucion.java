package unlu.sip.pga.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unlu.sip.pga.models.enumerados.EstadoResolucion;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "resolucion")
public class Resolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResolucion")
    private Integer idResolucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEjercicio", nullable = false)
    private Ejercicio ejercicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaResolucion;

    @Column(length = 200)
    private String resolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoResolucion estado;  //CORRECTO, INCORRECTO

}
