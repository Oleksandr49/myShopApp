package shopApp.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.exceptions.DataException;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Cart;
import shopApp.model.user.customer.Customer;
import shopApp.model.user.customer.Details;
import shopApp.service.customer.CustomerService;
import shopApp.service.jwt.JwtService;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final String headerName = "Authorization";

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtService jwtService;


    @PostMapping
    public void create(@Valid @RequestBody Customer customer) throws DataException {
        customerService.create(customer);
    }


    @GetMapping("/details")
    public EntityModel<Details> readDetails(@RequestHeader(name = headerName)String header) {
        return customerService.readDetails(jwtService.getIdFromAuthHeader(header));
    }

    @PutMapping("/details")
    public EntityModel<Details>updateDetails(@Valid@RequestBody Details details, @RequestHeader(name = headerName)String header){
        return customerService.updateDetails(details, jwtService.getIdFromAuthHeader(header));
    }

    @GetMapping("/addresses")
    public Address readAddress(@RequestHeader(name = headerName)String header) {
        return customerService.readAddress(jwtService.getIdFromAuthHeader(header));
    }

    @PutMapping("/addresses")
    public Address updateAddress(@Valid @RequestBody Address address, @RequestHeader(name = headerName)String header){
        return customerService.updateAddress(address, jwtService.getIdFromAuthHeader(header));
    }

    @PutMapping("/carts/{productId}")
    public EntityModel<Cart> addToCart(@RequestHeader(name = headerName)String header, @PathVariable Long productId) {
        return customerService.addItemToCart(jwtService.getIdFromAuthHeader(header), productId);
    }

    @DeleteMapping("/carts/{itemId}")
    public EntityModel<Cart> removeFromCart(@RequestHeader(name = headerName)String header, @PathVariable Long itemId) {
        return customerService.removeItemFromCart(jwtService.getIdFromAuthHeader(header), itemId);
    }

    @GetMapping("/carts")
    public EntityModel<Cart> readShoppingCart(@RequestHeader(name = headerName)String header) {
        return customerService.readCart(jwtService.getIdFromAuthHeader(header));
    }

    @PostMapping("/carts")
    public EntityModel<CustomerOrder> confirmCart(@RequestHeader(name = headerName)String header) {
        return customerService.CartToOrder(jwtService.getIdFromAuthHeader(header));
    }

    @DeleteMapping("/carts")
    public EntityModel<Cart> emptyCart(@RequestHeader(name = headerName)String header) {
        return customerService.emptyCart(jwtService.getIdFromAuthHeader(header));
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<CustomerOrder>> readOrderHistory(@RequestHeader(name = headerName)String header) {
        return customerService.getOrderHistory(jwtService.getIdFromAuthHeader(header));
    }

    @GetMapping("/orders/{orderId}")
    public EntityModel<CustomerOrder> readOrder(@RequestHeader(name = headerName)String header, @PathVariable Long orderId) {
        return customerService.readOrder(jwtService.getIdFromAuthHeader(header), orderId);
    }
}
