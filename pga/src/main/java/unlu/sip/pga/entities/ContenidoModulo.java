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
@Table(name = "contenido_modulo")
public class ContenidoModulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContenido;

    @ManyToOne
    @JoinColumn(name = "idModulo", nullable = false)
    private Modulo modulo;

    @Column(length = 20)
    private String tipo; // PDF, Video, Texto, etc.

    @Column(length = 200)
    private String recurso; // URL del recurso
}