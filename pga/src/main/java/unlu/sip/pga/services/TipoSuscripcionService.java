package unlu.sip.pga.services;

import java.util.List;
import java.util.Optional;

import unlu.sip.pga.entities.TipoSuscripcion;

public interface TipoSuscripcionService {
    List<TipoSuscripcion> listarTiposSuscripcion();
    TipoSuscripcion createTipoSuscripcion(TipoSuscripcion tipoSuscripcion);
    Optional<TipoSuscripcion> getTipoSuscripcion(Integer id);
}
