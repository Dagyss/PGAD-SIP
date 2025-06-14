package unlu.sip.pga.services;

import unlu.sip.pga.dto.CodigoUsuarioDTO;
import unlu.sip.pga.dto.CodigoUsuarioEvaluacionDTO;

public interface CorregirEvaluacionService {
    String enviarTarea(CodigoUsuarioEvaluacionDTO request) throws Exception;
}