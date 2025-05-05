package unlu.sip.pga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlu.sip.pga.entities.TipoUsuario;

public interface TipoUsuarioRepository
        extends JpaRepository<TipoUsuario, String> {

}