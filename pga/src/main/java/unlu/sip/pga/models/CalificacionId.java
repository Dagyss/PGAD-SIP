package unlu.sip.pga.models;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CalificacionId implements Serializable {
    private Integer idCurso;
    private String idUsuario;

    public CalificacionId() {}
    public CalificacionId(Integer idCurso, String idUsuario) {
        this.idCurso   = idCurso;
        this.idUsuario = idUsuario;
    }
    // getters/setters, equals(), hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalificacionId)) return false;
        CalificacionId that = (CalificacionId) o;
        return Objects.equals(idUsuario, that.idUsuario)
                && Objects.equals(idCurso, that.idCurso);
    }
    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idCurso);
    }
}
