package unlu.sip.pga.services;

import unlu.sip.pga.entities.Permiso;
import java.util.List;
import java.util.Optional;

public interface PermisoService {
    Permiso crearPermiso(Permiso permiso);
    Optional<Permiso> obtenerPermisoPorId(Long id);
    List<Permiso> listarPermisos();
    Permiso actualizarPermiso(Permiso permiso);
    void eliminarPermiso(Long id);
}
