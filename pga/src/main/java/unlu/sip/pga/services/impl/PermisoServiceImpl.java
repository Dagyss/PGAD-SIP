package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Permiso;
import unlu.sip.pga.services.PermisoService;
import unlu.sip.pga.repositories.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoServiceImpl implements PermisoService {
    @Autowired private PermisoRepository permisoRepository;
    public Permiso crearPermiso(Permiso p) { return permisoRepository.save(p); }
    public Optional<Permiso> obtenerPermisoPorId(Long id) { return permisoRepository.findById(id); }
    public List<Permiso> listarPermisos() { return permisoRepository.findAll(); }
    public Permiso actualizarPermiso(Permiso p) { return permisoRepository.save(p); }
    public void eliminarPermiso(Long id) { permisoRepository.deleteById(id); }
}