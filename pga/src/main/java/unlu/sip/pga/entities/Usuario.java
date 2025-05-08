package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    private String id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false, unique = true)
    private String correo;

    @OneToMany(mappedBy = "usuario")
    @Column(length = 10)
    private List<Suscripcion> suscripciones; // free/premium

    @Column
    private String nivelConocimiento;

    @Column
    private Boolean estadoCuenta;

    @ManyToMany
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private Set<Rol> roles;
}
