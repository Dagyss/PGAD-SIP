package unlu.sip.pga.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.BacksUrlDTO;
import unlu.sip.pga.dto.MpNotifyDTO;
import unlu.sip.pga.services.MercadoPagoService;

import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/v1/mercadopago")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    // @PostMapping("/process")
    // public void processPayment(@RequestBody Map<String, Object> cardFormData) {
    //     PaymentClient client = new PaymentClient();

    //     PaymentCreateRequest paymentCreateRequest =
    //       PaymentCreateRequest.builder()
    //           .transactionAmount(request.getTransactionAmount())
    //           .token(request.getToken())
    //           .description(request.getDescription())
    //           .installments(request.getInstallments())
    //           .paymentMethodId(request.getPaymentMethodId())
    //           .payer(
    //               PaymentPayerRequest.builder()
    //                   .email(request.getPayer().getEmail())
    //                   .firstName(request.getPayer().getFirstName())
    //                   .identification(
    //                       IdentificationRequest.builder()
    //                           .type(request.getPayer().getIdentification().getType())
    //                           .number(request.getPayer().getIdentification().getNumber())
    //                           .build())
    //                   .build())
    //           .build();
    
    //     client.create(paymentCreateRequest);
    // }


    @PostMapping("/preference")
    public ResponseEntity<String> getIdPreference(@RequestBody Map<String, Object> cardFormData) {
        // Crear una instancia del logger
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Datos recibidos: {}", cardFormData);
        String preferenceId = "1234";
        String titulo = "curso";
        int cantidad = 1;
        BigDecimal precio = new BigDecimal("1000");
        String currency = "ARS";

        try {
            BacksUrlDTO backsUrl = new BacksUrlDTO();
            backsUrl.setSuccess("https://localhost:5173/success");
            backsUrl.setPending("https://localhost:5173/pending");
            backsUrl.setFailure("https://localhost:5173/failed");

            // Llamada al servicio de MercadoPago para crear una preferencia de pago
            preferenceId = this.mercadoPagoService.createPreference(
                titulo, cantidad,currency,
                    precio, backsUrl,
                    "https://localhost:6060/api/v1/mercadopago/notify");

            //Dentro de este metodo podemos recibir información para pasarle por params al controlador de notify una vew que se haya realizado el pago, ej:el username del usuario,

            // Si se crea la preferencia correctamente, retornamos el ID
            return ResponseEntity.ok(preferenceId);
        } catch (MPException | MPApiException e) {
            // Capturamos excepciones específicas de MercadoPago
            logger.error("Error creando preferencia de pago en MercadoPago", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creando preferencia de pago en MercadoPago: " + e.getMessage());
        } catch (Exception e) {
            // Capturamos cualquier otra excepción
            logger.error("Error inesperado creando preferencia de pago", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado creando preferencia de pago: " + e.getMessage());
        }

    }

    @PostMapping(value = "notify")
    public void notifyPay(@RequestBody MpNotifyDTO mpNotify) {
        // Crear una instancia del logger para registrar información y eventos.
        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Registrar la notificación recibida.
        // Esto imprime la información de la notificación de pago a los registros
        // (logs).
        logger.info(mpNotify.toString());

        // Aquí recibimos la notificación del pago de MercadoPago.
        // Podemos realizar cualquier acción necesaria con esta información,
        // como guardar los detalles del pago en la base de datos,
        // actualizar el estado de una orden, enviar notificaciones a los usuarios, etc.
    }

}