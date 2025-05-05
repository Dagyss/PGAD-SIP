package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.services.ResolucionService;
import unlu.sip.pga.repositories.ResolucionRepository;
import unlu.sip.pga.entities.Resolucion;
import java.util.List;
import java.util.Optional;

@Service
public class ResolucionServiceImpl implements ResolucionService {
    @Autowired private ResolucionRepository repo;

    @Override public Resolucion crear(Resolucion r) { return repo.save(r); }
    @Override public Optional<Resolucion> obtenerPorId(Integer id) {
        return repo.findById(id);
    }
    @Override public List<Resolucion> listarPorIdUsuario(String idUsuario) {
        return repo.findByUsuario_Id(idUsuario);
    }
    @Override public List<Resolucion> listarPorIdEjercicio(Integer idEjercicio) {
        return repo.findByEjercicio_Id(idEjercicio);
    }
    @Override public void eliminar(Integer idResolucion) {
        repo.deleteById(idResolucion);
    }
}