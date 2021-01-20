package shopApp.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopApp.service.customer.CustomerService;
import shopApp.service.customer.order.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers/orders")
public class OrderController {

    private final String headerName = "Authorization";
    final private CustomerService customerService;
    final private OrderService orderService;
}
