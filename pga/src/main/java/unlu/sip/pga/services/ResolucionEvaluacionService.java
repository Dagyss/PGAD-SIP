package unlu.sip.pga.services;

import unlu.sip.pga.dto.ResolucionEvaluacionDTO;
import java.util.List;
import java.util.Optional;

public interface ResolucionEvaluacionService {
    ResolucionEvaluacionDTO crear(ResolucionEvaluacionDTO dto);
    Optional<ResolucionEvaluacionDTO> obtenerPorId(Integer id);
    List<ResolucionEvaluacionDTO> listarTodas();
    ResolucionEvaluacionDTO actualizar(Integer id, ResolucionEvaluacionDTO dto);
    void eliminar(Integer id);
}
