package com.example.firstproject.controller.paypal;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.service.paypal.PayPallClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class PayPallController {

    @Autowired
    private PayPallClient payPalClient;

    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestBody CustomerOrder customerOrder){
        return payPalClient.createPayment(String.valueOf(customerOrder.getTotalCost()));
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return payPalClient.completePayment(request);
    }
}
