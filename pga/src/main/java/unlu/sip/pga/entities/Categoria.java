package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.*;
import unlu.sip.pga.models.enumerados.TipoCategoria;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCategoria tipo;  // LENGUAJE o TEMA
}