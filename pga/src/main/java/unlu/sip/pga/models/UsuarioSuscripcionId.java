package unlu.sip.pga.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioSuscripcionId implements Serializable {
    private String idUsuario;
    private Integer idSuscripcion;

    public UsuarioSuscripcionId() {}
    public UsuarioSuscripcionId(String idUsuario, Integer idSuscripcion) {
        this.idUsuario = idUsuario;
        this.idSuscripcion = idSuscripcion;
    }

    // getters y setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioSuscripcionId)) return false;
        UsuarioSuscripcionId that = (UsuarioSuscripcionId) o;
        return Objects.equals(idUsuario, that.idUsuario)
                && Objects.equals(idSuscripcion, that.idSuscripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idSuscripcion);
    }
}