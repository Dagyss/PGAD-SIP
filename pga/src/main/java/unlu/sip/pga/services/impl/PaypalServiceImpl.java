package unlu.sip.pga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.*;
import org.springframework.stereotype.Service;
import unlu.sip.pga.services.PaypalService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class PaypalServiceImpl implements PaypalService {
    private final PaypalServerSdkClient paypalClient;
    private final ObjectMapper objectMapper;

    public PaypalServiceImpl(PaypalServerSdkClient paypalClient, ObjectMapper objectMapper) {
        this.paypalClient = paypalClient;
        this.objectMapper = objectMapper;
    }

    public Order createOrder(Map<String, Object> request) throws IOException, ApiException {
        String reqString = objectMapper.writeValueAsString(request);
        System.out.println("Creando orden de PayPal...");
        CreateOrderInput createOrderInput = new CreateOrderInput.Builder(
         null,
         new OrderRequest.Builder(
             CheckoutPaymentIntent.fromString("CAPTURE"),
             Arrays.asList(
                 new PurchaseUnitRequest.Builder(
                     new AmountWithBreakdown.Builder(
                         "USD",
                         "100"
                     )
                     .breakdown(
                       new AmountBreakdown.Builder()
                           .itemTotal(
                               new Money(
                                   "USD",
                                   "100"
                               )
                           ).build()
                     )
                     .build()
                 )
                 .items(
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
        CaptureOrderInput ordersCaptureInput = new CaptureOrderInput.Builder(
                orderID,
                null)
                .build();
        OrdersController ordersController = paypalClient.getOrdersController();
        ApiResponse<Order> apiResponse = ordersController.captureOrder(ordersCaptureInput);
        return apiResponse.getResult();
    }
}