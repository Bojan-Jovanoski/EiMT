package bojan.jovanoski.emt.lab1.Service.Implementation;

import bojan.jovanoski.emt.lab1.Service.PaymentService;
import bojan.jovanoski.emt.lab1.dto.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Value("${STRIPE_SECRET_KEY}")
    private String stripePrivateKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripePrivateKey;
    }

    @Override
    public Charge charge(ChargeRequest chargeRequest) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);

    }
}