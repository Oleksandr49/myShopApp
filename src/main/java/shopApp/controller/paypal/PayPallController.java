package shopApp.controller.paypal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopApp.model.order.CustomerOrder;
import shopApp.service.paypal.PayPallClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PayPallController {

    private final PayPallClient payPalClient;

    @PostMapping("/payment/make")
    public Map<String, Object> makePayment(@RequestBody CustomerOrder customerOrder){
        return payPalClient.createPayment(String.valueOf(customerOrder.getTotalCost()));
    }

    @PostMapping("/payment/complete")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return payPalClient.completePayment(request);
    }
}
