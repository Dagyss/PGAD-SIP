package unlu.sip.pga.controllers;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unlu.sip.pga.dto.BacksUrlDTO;
import unlu.sip.pga.dto.MpNotifyDTO;
import unlu.sip.pga.services.MercadoPagoService;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        String preferenceId = "ocurrio un error";
        String titulo = "curso";
        int cantidad = 1;
        BigDecimal precio = new BigDecimal("1000");
        String currency = "ARS";

        try {
            BacksUrlDTO backsUrl = new BacksUrlDTO();
            backsUrl.setSuccess("URL DEL FRONTEND /success");
            backsUrl.setPending("URL DEL FRONTEND /pending");
            backsUrl.setFailure("URL DEL FRONTEND /failed");

            // Llamada al servicio de MercadoPago para crear una preferencia de pago
            preferenceId = this.mercadoPagoService.createPreference(
                titulo, cantidad,currency,
                    precio, backsUrl,
                    "https://95d8-2803-9800-9803-5091-9552-4a64-f4f0-b9e.ngrok-free.app/api/v1/mercadopago/notify");

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