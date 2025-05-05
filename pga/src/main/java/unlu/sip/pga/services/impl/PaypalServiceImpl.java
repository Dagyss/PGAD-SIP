package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unlu.sip.pga.entities.Pago;
import unlu.sip.pga.entities.TransactionDetails;
import unlu.sip.pga.repositories.PagoRepository;
import unlu.sip.pga.services.PaypalService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PaypalServiceImpl implements PaypalService {
    @Autowired
    private PagoRepository pagoRepository;

    private final PaypalServerSdkClient paypalClient;
    private final ObjectMapper objectMapper;

    public PaypalServiceImpl(PaypalServerSdkClient paypalClient, ObjectMapper objectMapper) {
        this.paypalClient = paypalClient;
        this.objectMapper = objectMapper;
    }

    public Order createOrder(Map<String, Object> request) throws IOException, ApiException {
        String reqString = objectMapper.writeValueAsString(request);
        System.out.println("Creando orden de PayPal...");
        List<Item> items =
        // lookup item details in `cart` from database
            Arrays.asList(
                new Item.Builder(
                    "T-Shirt",
                    new Money.Builder("USD","100").build(),
                    "1"
                )
                .description("Super Fresh Shirt")
                .sku("sku01")
                .category(ItemCategory.PHYSICAL_GOODS)
                .build()
            );
        AmountBreakdown breakdown = new AmountBreakdown.Builder().itemTotal(
             new Money("USD", "100")).build();
        AmountWithBreakdown monto = new AmountWithBreakdown.Builder("USD", "100")
                                    .breakdown(breakdown).build();
        PurchaseUnitRequest unidad = new PurchaseUnitRequest.Builder(monto).items(items).build();

        OrderRequest order =          
            new OrderRequest.Builder(
                CheckoutPaymentIntent.fromString("CAPTURE"),
                Arrays.asList(unidad)
            ).build();
        CreateOrderInput createOrderInput = 
            new CreateOrderInput.Builder(null, order).build();

        // guardar en la bd
        Pago pago = new Pago();
        pago.setId(createOrderInput.getPaypalRequestId());
        pago.setDateTimeCreated(LocalDateTime.now()); 
        pago.setStatus("CREATED");
        pago.setStatusDetail("Order created successfully");
        // pago.setTransactionDetails(new TransactionDetails(
        //         createOrderInput.get
        // ));

        OrdersController ordersController = paypalClient.getOrdersController();
        ApiResponse<Order> apiResponse = ordersController.createOrder(createOrderInput);
        return apiResponse.getResult();
    }

    public Order captureOrders(String orderID) throws IOException, ApiException {
        CaptureOrderInput ordersCaptureInput = new CaptureOrderInput.Builder(
                orderID,
                null)
                .build();
        OrdersController ordersController = paypalClient.getOrdersController();
        ApiResponse<Order> apiResponse = ordersController.captureOrder(ordersCaptureInput);
        return apiResponse.getResult();
    }
}