package unlu.sip.pga.services;

import unlu.sip.pga.entities.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {
    Rol crearRol(Rol rol);
    Optional<Rol> obtenerRolPorId(Integer id);
    List<Rol> listarRoles();
    Rol actualizarRol(Rol rol);
    void eliminarRol(Integer id);
}
