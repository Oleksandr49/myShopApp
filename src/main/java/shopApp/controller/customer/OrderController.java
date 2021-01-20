package shopApp.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.order.CustomerOrder;
import shopApp.service.customer.order.OrderService;
import shopApp.service.customer.order.OrderWrapper;
import shopApp.service.jwt.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers/orders")
public class OrderController {

    private final String headerName = "Authorization";

    final private OrderService orderService;
    final private OrderWrapper orderWrapper;
    final private JwtService jwtService;


    @PostMapping
    public EntityModel<CustomerOrder> orderCart(@RequestHeader(name = headerName)String header) {
        return orderWrapper.toModel(orderService.orderCart(jwtService.getCustomerIdFromAuthHeader(header)));
    }

    @GetMapping
    public CollectionModel<EntityModel<CustomerOrder>> readOrderHistory(@RequestHeader(name = headerName)String header) {
        return orderWrapper.toEntityCollection(orderService.getOrderHistory(jwtService.getCustomerIdFromAuthHeader(header)));
    }

    @GetMapping("/{orderId}")
    public EntityModel<CustomerOrder> readOrder(@RequestHeader(name = headerName)String header, @PathVariable Long orderId) {
        return orderWrapper.toModel(orderService.readOrder(jwtService.getCustomerIdFromAuthHeader(header), orderId));
    }
}
