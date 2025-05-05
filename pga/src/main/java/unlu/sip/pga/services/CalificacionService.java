package unlu.sip.pga.services;

import unlu.sip.pga.entities.Calificacion;
import java.util.List;
import java.util.Optional;

public interface CalificacionService {
    Calificacion crear(Calificacion c);
    Optional<Calificacion> obtener(Integer idCurso, String idUsuario);
    List<Calificacion> listarPorUsuario(String idUsuario);
    List<Calificacion> listarPorCurso(Integer idCurso);
    void eliminar(Integer idCurso, String idUsuario);
}