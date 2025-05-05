package unlu.sip.pga.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unlu.sip.pga.services.CalificacionService;
import unlu.sip.pga.repositories.CalificacionRepository;
import unlu.sip.pga.entities.Calificacion;
import unlu.sip.pga.models.CalificacionId;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionServiceImpl implements CalificacionService {
    @Autowired private CalificacionRepository repo;

    @Override
    public Calificacion crear(Calificacion c) {
        return repo.save(c);
    }
    @Override
    public Optional<Calificacion> obtener(Integer idCurso, String idUsuario) {
        return repo.findById(new CalificacionId(idCurso, idUsuario));
    }
    @Override
    public List<Calificacion> listarPorUsuario(String idUsuario) {
        return repo.findByUsuario_Id(idUsuario);
    }
    @Override
    public List<Calificacion> listarPorCurso(Integer idCurso) {
        return repo.findByCurso_Id(idCurso);
    }
    @Override
    public void eliminar(Integer idCurso, String idUsuario) {
        repo.deleteById(new CalificacionId(idCurso, idUsuario));
    }
}