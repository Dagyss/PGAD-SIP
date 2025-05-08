package unlu.sip.pga.services;

import unlu.sip.pga.entities.SuscripcionUsuario;
import java.util.List;

public interface SuscripcionUsuarioService {
    SuscripcionUsuario crear(SuscripcionUsuario su);
    List<SuscripcionUsuario> listarPorUsuario(String idUsuario);
    List<SuscripcionUsuario> listarPorSuscripcion(Integer idSuscripcion);
    void eliminar(String idUsuario, Integer idSuscripcion);
}