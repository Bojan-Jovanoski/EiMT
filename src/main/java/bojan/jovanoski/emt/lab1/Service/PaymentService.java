package bojan.jovanoski.emt.lab1.Service;

import bojan.jovanoski.emt.lab1.dto.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;


public interface PaymentService {
    Charge charge(ChargeRequest chargeRequest) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException, com.stripe.exception.CardException;
}
