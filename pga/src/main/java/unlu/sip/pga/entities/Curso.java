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
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCurso;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 200)
    private String descripcion;

    @Column
    private Float duracion; // en horas

    @Column(length = 15)
    private String nivel;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column
    private Float calificacion;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Modulo> modulos;
}
