
package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.repositories.PagoRepository;
import unlu.sip.pga.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    public Pago crearPago(Pago p) {return pagoRepository.save(p);}
    public Optional<Pago> obtenerPagoPorId(String paymentId) {return pagoRepository.findById(paymentId);}
    public List<Pago> listarPagos() {return pagoRepository.findAll();}
    public List<Pago> listarPagosPorUsuario(String idUsuario) {return pagoRepository.findByUsuarioId(idUsuario);}
    public Pago actualizarPago(Pago p) {return pagoRepository.save(p);}
    public void eliminarPago(String paymentId) { pagoRepository.deleteById(paymentId);}
}
