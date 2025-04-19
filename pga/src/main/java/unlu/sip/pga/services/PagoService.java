package unlu.sip.pga.services;

import unlu.sip.pga.entities.Pago;
import java.util.List;
import java.util.Optional;

public interface PagoService {
    Pago crearPago(Pago pago);
    Optional<Pago> obtenerPagoPorId(Long id);
    List<Pago> listarPagosPorUsuario(Long usuarioId);
    Pago actualizarPago(Pago pago);
    void eliminarPago(Long id);
}
