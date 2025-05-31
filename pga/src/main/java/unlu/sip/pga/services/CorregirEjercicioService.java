package unlu.sip.pga.services;

import unlu.sip.pga.dto.CodigoUsuarioDTO;

public interface CorregirEjercicioService {
    String enviarTarea(CodigoUsuarioDTO request) throws Exception;
}