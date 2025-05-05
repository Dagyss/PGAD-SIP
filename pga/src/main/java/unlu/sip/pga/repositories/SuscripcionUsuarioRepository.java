package unlu.sip.pga.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.SuscripcionUsuario;
import unlu.sip.pga.models.UsuarioSuscripcionId;
import java.util.List;

public interface SuscripcionUsuarioRepository
        extends JpaRepository<SuscripcionUsuario, UsuarioSuscripcionId> {

    List<SuscripcionUsuario> findByUsuario_Id(String idUsuario);

    List<SuscripcionUsuario> findBySuscripcion_Id(Integer idSuscripcion);
}