package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluacion")
@PrimaryKeyJoinColumn(name = "id")
@SuperBuilder
@Getter
@Setter
public class Evaluacion extends Ejercicio {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;

}