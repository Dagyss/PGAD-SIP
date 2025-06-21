package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.entities.TipoSuscripcion;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.repositories.PagoRepository;
import unlu.sip.pga.repositories.TipoSuscripcionRepository;
import unlu.sip.pga.repositories.UsuarioRepository;
import unlu.sip.pga.services.PaypalService;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Date;

@Service
public class PaypalServiceImpl implements PaypalService {
    private final PaypalServerSdkClient paypalClient;
    private final ObjectMapper objectMapper;
    private final TipoSuscripcionRepository suscripcionRepo;
    private final UsuarioRepository usuarioRepo;
    private final PagoRepository pagoRepo;

    public PaypalServiceImpl(
        PaypalServerSdkClient paypalClient,
        ObjectMapper objectMapper,
        TipoSuscripcionRepository suscripcionRepo,
        UsuarioRepository usuarioRepo,
        PagoRepository pagoRepo) {
        this.paypalClient = paypalClient;
        this.objectMapper = objectMapper;
        this.suscripcionRepo = suscripcionRepo;
        this.usuarioRepo = usuarioRepo;
        this.pagoRepo = pagoRepo;
    }

    public Order createOrder(Map<String, Object> request) throws IOException, ApiException {
        Integer suscripcionId = Integer.parseInt(request.get("suscripcionId").toString());

        TipoSuscripcion tipo = suscripcionRepo.findById(suscripcionId)
            .orElseThrow(() -> new IllegalArgumentException("Tipo de suscripción no encontrado")); 
        
        String precio = String.format("%.2f", tipo.getPrecio());
        
        CreateOrderInput createOrderInput = new CreateOrderInput.Builder(
         null,
         new OrderRequest.Builder(
             CheckoutPaymentIntent.fromString("CAPTURE"),
             Arrays.asList(
                 new PurchaseUnitRequest.Builder(
                     new AmountWithBreakdown.Builder("USD",precio)
                     .breakdown(
                       new AmountBreakdown.Builder()
                           .itemTotal(new Money("USD",precio))
                           .build()
                     )
                     .build()
                 )
                 .items(
                     // lookup item details in `cart` from database
                     Arrays.asList(
                         new Item.Builder(
                             tipo.getTipoSuscripcion(),
                             new Money.Builder("USD",precio).build(),
                             "1"
                         )
                         .description("Suscripción " + tipo.getTipoSuscripcion())
                         .sku("sub-"+tipo.getId())
                         .category(ItemCategory.DIGITAL_GOODS)
                         .build()
                     )
                   )
                 
                 .build()
             )
         )
         
         
         .build()
     ).build();
   OrdersController ordersController = paypalClient.getOrdersController();
   ApiResponse<Order> apiResponse = ordersController.createOrder(createOrderInput);
   return apiResponse.getResult();
}

    public Order captureOrders(String orderID) throws IOException, ApiException {
        System.out.println(">>> Entró a captureOrders con ID: " + orderID);
        OrdersController ordersController = paypalClient.getOrdersController();

        CaptureOrderInput ordersCaptureInput = new CaptureOrderInput.Builder(orderID,null)
                .build();
        ApiResponse<Order> apiResponse = ordersController.captureOrder(ordersCaptureInput);
        Order order = apiResponse.getResult();
        System.out.println("DEBUG Order completo: " + new ObjectMapper().writeValueAsString(order));

        System.out.println("Order creada con ID: " + order.getId());
        
        PurchaseUnit purchaseUnit = order.getPurchaseUnits().get(0);
        OrdersCapture capture = purchaseUnit.getPayments().getCaptures().get(0);
        String paymentId = capture.getId();
        String status = capture.getStatus().toString();
        String createTimeStr = capture.getCreateTime();
        String updateTimeStr = capture.getUpdateTime();
        
        System.out.println("createTimeStr: " + createTimeStr);
        System.out.println("updateTimeStr: " + updateTimeStr);

        Date createTime = parsePaypalDate(createTimeStr);
        Date updateTime = parsePaypalDate(updateTimeStr);

        System.out.println("Parsed createTime: " + createTime);
        System.out.println("Parsed updateTime: " + updateTime);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;
        if (auth.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getSubject();
        }

        if (userId==null) {
            throw new RuntimeException("No se pudo obtener el ID de usuario del contexto de seguridad");
        }
        
        Usuario user = usuarioRepo.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Pago pago = Pago.builder()
        .id(paymentId)
        .status(status)
        .dateCreated(createTime)
        .dateApproved(updateTime)
        .moneyReleaseDate(null)
        .statusDetail(capture.getStatusDetails() != null ?
        capture.getStatusDetails().getReason().name() : null)
        .usuario(user)
        .build();
        
        pagoRepo.save(pago);

        Pago saved = pagoRepo.findById(paymentId).orElseThrow();
        System.out.println(">> Pago guardado: " + saved);

        System.out.println("Order creada con ID: " + order.getId());
        return order;
    }

    private Date parsePaypalDate(String dateStr) {
        if (dateStr==null) {
            return null;
        }
        try {
            Instant instant = Instant.parse(dateStr);
            return Date.from(instant);
        } catch (Exception e) {
            return null;
        }
    }

}