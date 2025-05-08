package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unlu.sip.pga.models.CalificacionId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "calificacion")
public class Calificacion {

    @EmbeddedId
    private CalificacionId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @MapsId("idCurso")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idCurso")
    private Curso curso;

    @Column(nullable = false)
    private Integer calificacion;  // 1–5

    @Column(length = 100)
    private String comentario;

}