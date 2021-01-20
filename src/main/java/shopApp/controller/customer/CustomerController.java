package shopApp.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Customer;
import shopApp.service.customer.CustomerService;
import shopApp.service.jwt.JwtService;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final String headerName = "Authorization";

    final private CustomerService customerService;
    final private JwtService jwtService;

    @PostMapping
    public void create(@Valid @RequestBody Customer customer) throws PersistenceException {
        customerService.create(customer);
    }

    @PostMapping("/carts")
    public EntityModel<CustomerOrder> confirmCart(@RequestHeader(name = headerName)String header) {
        return customerService.CartToOrder(jwtService.getCustomerIdFromAuthHeader(header));
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<CustomerOrder>> readOrderHistory(@RequestHeader(name = headerName)String header) {
        return customerService.getOrderHistory(jwtService.getCustomerIdFromAuthHeader(header));
    }

    @GetMapping("/orders/{orderId}")
    public EntityModel<CustomerOrder> readOrder(@RequestHeader(name = headerName)String header, @PathVariable Long orderId) {
        return customerService.readOrder(jwtService.getCustomerIdFromAuthHeader(header), orderId);
    }
}
