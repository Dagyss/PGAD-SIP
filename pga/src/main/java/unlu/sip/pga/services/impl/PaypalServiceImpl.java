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
import unlu.sip.pga.entities.Suscripcion;
import unlu.sip.pga.entities.TipoSuscripcion;
import unlu.sip.pga.entities.TransactionDetails;
import unlu.sip.pga.entities.Usuario;
import unlu.sip.pga.repositories.PagoRepository;
import unlu.sip.pga.repositories.SuscripcionRepository;
import unlu.sip.pga.repositories.TipoSuscripcionRepository;
import unlu.sip.pga.repositories.UsuarioRepository;
import unlu.sip.pga.services.PaypalService;
import unlu.sip.pga.services.UsuarioService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PaypalServiceImpl implements PaypalService {
    private final PaypalServerSdkClient paypalClient;
    private final ObjectMapper objectMapper;
    private final TipoSuscripcionRepository suscripcionRepo;
    private final SuscripcionRepository suscripcionesRepo;
    private final UsuarioRepository usuarioRepo;
    private final PagoRepository pagoRepo;
    private final UsuarioService usuarioService;

    public PaypalServiceImpl(
        PaypalServerSdkClient paypalClient,
        UsuarioService usuarioService,
        ObjectMapper objectMapper,
        TipoSuscripcionRepository suscripcionRepo,
        UsuarioRepository usuarioRepo,
        PagoRepository pagoRepo,
        SuscripcionRepository suscripcionesRepo) {
        this.paypalClient = paypalClient;
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
        this.suscripcionRepo = suscripcionRepo;
        this.usuarioRepo = usuarioRepo;
        this.pagoRepo = pagoRepo;
        this.suscripcionesRepo = suscripcionesRepo;
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
   System.out.println(">>> Order creada: " + apiResponse.getResult().getId());
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

        String netValueStr = capture.getSellerReceivableBreakdown().getNetAmount().getValue();
        String totalPaidStr = capture.getSellerReceivableBreakdown().getGrossAmount().getValue();

        BigDecimal netAmount;
        BigDecimal totalPaidAmount;

        try {
            netAmount = new BigDecimal(netValueStr);
            totalPaidAmount = new BigDecimal(totalPaidStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error al parsear valores monetarios: net=" + netValueStr + ", total=" + totalPaidStr, e);
        }

        System.out.println("Valor bruto: " + capture.getSellerReceivableBreakdown().getNetAmount().getValue());
        System.out.println("netAmount: " + netAmount);
        System.out.println("totalPaidAmount: " + totalPaidAmount);



        TransactionDetails transactionDetails = TransactionDetails.builder()
        .netReceivedAmount(netAmount)
        .totalPaidAmount(totalPaidAmount)
        .build();
        
        Pago pago = Pago.builder()
        .id(paymentId)
        .status(status)
        .dateCreated(createTime)
        .dateApproved(updateTime)
        .moneyReleaseDate(null)
        .statusDetail(capture.getStatusDetails() != null ?
        capture.getStatusDetails().getReason().name() : null)
        .transactionDetails(transactionDetails)
        .usuario(user)
        .build();
        
        pagoRepo.save(pago);

        // Pago saved = pagoRepo.findById(paymentId).orElseThrow();
        // System.out.println(">> Pago guardado: " + saved);

        System.out.println("Order creada con ID: " + order.getId());

        LocalDateTime localDateTime = createTime.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();

        // Sumar 1 año
        LocalDateTime fechaFinLocal = localDateTime.plusYears(1);

        // Convertir de nuevo a Date
        Date fechaFin = Date.from(fechaFinLocal.atZone(ZoneId.systemDefault()).toInstant());

        List<TipoSuscripcion> tiposSuscripcion = suscripcionRepo.findAll();
        TipoSuscripcion tipoSuscripcion = tiposSuscripcion.isEmpty() ?
            null : tiposSuscripcion.get(0);

        System.out.println(">>> Tipo de suscripción: " + tipoSuscripcion);

        Suscripcion suscripcion = Suscripcion.builder()
        .usuario(user)
        .suscripcion(tipoSuscripcion)
        .fechaInicio(createTime)
        .fechaFin(fechaFin)
        .build();
        
        suscripcionesRepo.save(suscripcion);
        String token = usuarioService.obtenerTokenManagementApi();
        String roleId = usuarioService.obtenerRoleId(token,"premiumUser");
        usuarioService.asignarRolEnAuth0(userId, roleId, token);
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