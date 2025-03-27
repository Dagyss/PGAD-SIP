package unlu.sip.pga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mercadopago.resources.payment.Payment;
import unlu.sip.pga.pago.PagoRequest;
import unlu.sip.pga.pago.PagoServ;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PagosController {

    private final PagoServ pagoService;

    @Autowired
    public PagosController(PagoServ pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/process")
    public ResponseEntity<Payment> procesarPago(@RequestBody PagoRequest request) {
        try {
            Payment payment = pagoService.procesarPago(request);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}