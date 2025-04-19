package unlu.sip.pga.services;


import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.*;
import java.io.IOException;
import java.util.Map;

public interface PaypalService {
    Order createOrder(Map<String,Object> request)
            throws IOException, ApiException;


    Order captureOrders(String orderID)
            throws IOException, ApiException;
}