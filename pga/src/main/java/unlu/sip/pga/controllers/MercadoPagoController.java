package unlu.sip.pga.controllers;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import unlu.sip.pga.dto.BacksUrlDTO;
import unlu.sip.pga.dto.MpNotifyDTO;
import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.entities.TransactionDetails;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.repositories.PagoRepository;
import unlu.sip.pga.services.MercadoPagoService;
import unlu.sip.pga.services.UsuarioService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://34.10.220.212.nip.io:5173")
@RestController
@RequestMapping("/api/v1/mercadopago")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagoRepository pagoRepository;

    @Value("${application.client-origin-url}")
    private String frontendUrl;

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
    public ResponseEntity<Map<String, String>> getIdPreference(@RequestBody Map<String, Object> data) {
        String titulo = "Suscripción";
        int cantidad = 1;
        String currency = "ARS";
        BigDecimal precio = new BigDecimal(String.valueOf(data.get("transaction_amount")));

        try {
            BacksUrlDTO backsUrl = new BacksUrlDTO();
            backsUrl.setSuccess("https://34.10.220.212.nip.io/success");
            backsUrl.setPending("https://34.10.220.212.nip.io/pending");
            backsUrl.setFailure("https://34.10.220.212.nip.io/failed");

            // Llamada al servicio de MercadoPago para crear una preferencia de pago
            String initPoint = this.mercadoPagoService.createPreference(
                titulo, cantidad,currency, precio, backsUrl,
                "/api/v1/mercadopago/notify");
            
            Map<String, String> response = new HashMap<>();
            response.put("init_point", initPoint);
                

            //Dentro de este metodo podemos recibir información para pasarle por params al controlador de notify una vew que se haya realizado el pago, ej:el username del usuario,

            // Si se crea la preferencia correctamente, retornamos el ID
            return ResponseEntity.ok(response);
        } catch (MPException | MPApiException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error creando preferencia: " + e.getMessage());
            // Capturamos excepciones específicas de MercadoPago
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error inesperado: " + e.getMessage());
            // Capturamos cualquier otra excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
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
        String paymentId = mpNotify.getData().getId();
        try {
            PaymentClient client = new PaymentClient();
            Payment payment = client.get(Long.parseLong(paymentId));
            Pago pago = new Pago();
            pago.setId(payment.getId().toString());
            pago.setDateCreated(Date.from(payment.getDateCreated().toInstant()));
            pago.setDateApproved(Date.from(payment.getDateApproved().toInstant()));
            pago.setStatus(payment.getStatus());
            pago.setStatusDetail(payment.getStatusDetail());
            
            TransactionDetails transactionDetails = new TransactionDetails();
            transactionDetails.setNetReceivedAmount(payment.getTransactionDetails().getNetReceivedAmount());
            transactionDetails.setTotalPaidAmount(payment.getTransactionDetails().getTotalPaidAmount());
            pago.setTransactionDetails(transactionDetails);


            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = null;
            if (auth.getPrincipal() instanceof Jwt jwt) {
                userId = jwt.getSubject();
            }

            if (userId==null) {
                throw new RuntimeException("No se pudo obtener el ID de usuario del contexto de seguridad");
            }

            Usuario user = usuarioService.obtenerUsuarioPorId(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            pago.setUsuario(user);

            pagoRepository.save(pago);
            String token = usuarioService.obtenerTokenManagementApi();
            String roleId = usuarioService.obtenerRoleId(token, "premiumUser");
            usuarioService.asignarRolEnAuth0(userId,roleId,token);
        } catch (MPException | MPApiException e) {
            logger.error("error al consultar el pago ", e);
        }
    }

}