package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.services.PagoService;
import unlu.sip.pga.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired private PagoRepository pagoRepository;
    public Pago crearPago(Pago p) { return pagoRepository.save(p); }
    public Optional<Pago> obtenerPagoPorId(Long id) { return pagoRepository.findById(id); }
    public List<Pago> listarPagosPorUsuario(Long usuarioId) { return pagoRepository.findByUsuarioId(usuarioId); }
    public Pago actualizarPago(Pago p) { return pagoRepository.save(p); }
    public void eliminarPago(Long id) { pagoRepository.deleteById(id); }
}
