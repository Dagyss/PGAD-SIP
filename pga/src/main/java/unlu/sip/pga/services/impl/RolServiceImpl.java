package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Rol;
import unlu.sip.pga.services.RolService;
import unlu.sip.pga.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {
    @Autowired private RolRepository rolRepository;
    public Rol crearRol(Rol r) { return rolRepository.save(r); }
    public Optional<Rol> obtenerRolPorId(Long id) { return rolRepository.findById(id); }
    public List<Rol> listarRoles() { return rolRepository.findAll(); }
    public Rol actualizarRol(Rol r) { return rolRepository.save(r); }
    public void eliminarRol(Long id) { rolRepository.deleteById(id); }
}
