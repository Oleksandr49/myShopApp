package shopApp.service.paypal;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PaymentService {
    Map<String, Object> createPayment(String sum);
    Map<String, Object> completePayment(HttpServletRequest req);
}
