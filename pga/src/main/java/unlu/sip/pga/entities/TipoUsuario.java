package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unlu.sip.pga.models.enumerados.TipoAcceso;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario {

    @Id
    @Column(name = "idUsuario")
    private String idUsuario;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoUsuario", nullable = false, length = 15)
    private TipoAcceso tipoUsuario;

}