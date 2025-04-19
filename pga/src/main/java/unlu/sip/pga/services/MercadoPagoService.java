package unlu.sip.pga.services;


import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import unlu.sip.pga.dto.BacksUrlDTO;
import unlu.sip.pga.dto.PagoDTO;
import java.math.BigDecimal;


public interface MercadoPagoService {

    String createPreference(
            String title,
            Integer quantity,
            String currencyId,
            BigDecimal unitPrice,
            BacksUrlDTO backsUrl,
            String notificationUrl
    ) throws MPException, MPApiException;

    PagoDTO getPago(String paymentId);
}