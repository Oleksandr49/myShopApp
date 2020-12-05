package com.example.firstproject.controllers;

import com.example.firstproject.model.orders.CustomerOrder;
import com.example.firstproject.service.payment.PayPallClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
