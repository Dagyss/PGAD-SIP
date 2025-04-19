package unlu.sip.pga.config;

import com.paypal.sdk.Environment;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.authentication.ClientCredentialsAuthModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public PaypalServerSdkClient paypalClient() {
        String clientId = dotenv.get("PAYPAL_CLIENT_ID");
        String clientSecret = dotenv.get("PAYPAL_CLIENT_SECRET");

        return new PaypalServerSdkClient.Builder()
                .environment(Environment.SANDBOX)
                .clientCredentialsAuth(new ClientCredentialsAuthModel.Builder(clientId, clientSecret).build())
                .build();
    }
}