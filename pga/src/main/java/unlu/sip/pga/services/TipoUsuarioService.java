package unlu.sip.pga.services;

import unlu.sip.pga.entities.TipoUsuario;
import java.util.Optional;

public interface TipoUsuarioService {
    TipoUsuario crearOActualizar(TipoUsuario tu);
    Optional<TipoUsuario> obtenerPorUsuario(String idUsuario);
    void eliminarPorUsuario(String idUsuario);
}
