package unlu.sip.pga.services;

import unlu.sip.pga.entities.Pago;
import java.util.List;
import java.util.Optional;

public interface PagoService {
    Pago crearPago(Pago p);
    Optional<Pago> obtenerPagoPorId(String paymentId);
    List<Pago> listarPagos();
    List<Pago> listarPagosPorUsuario(Integer idUsuario);
    Pago actualizarPago(Pago p);
    void eliminarPago(String paymentId);
}
