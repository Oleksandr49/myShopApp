package eCommerce.myShopApplication.controller.customer;

import eCommerce.myShopApplication.model.order.CustomerOrder;
import eCommerce.myShopApplication.model.user.customer.Address;
import eCommerce.myShopApplication.model.user.customer.Cart;
import eCommerce.myShopApplication.model.user.customer.Customer;
import eCommerce.myShopApplication.model.user.customer.Details;
import eCommerce.myShopApplication.service.customer.CustomerService;
import eCommerce.myShopApplication.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final String headerName = "Authorization";

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Customer customer) {
        customerService.create(customer);
    }

    @GetMapping("/details")
    public EntityModel<Details> readDetails(@RequestHeader(name = headerName)String header) {
        return customerService.readDetails(jwtService.getIdFromAuthHeader(header));
    }

    @PutMapping("/details")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<Details>updateDetails(@RequestBody Details details, @RequestHeader(name = headerName)String header){
        return customerService.updateDetails(details, jwtService.getIdFromAuthHeader(header));
    }

    @GetMapping("/addresses")
    public Address readAddress(@RequestHeader(name = headerName)String header) {
        return customerService.readAddress(jwtService.getIdFromAuthHeader(header));
    }

    @PutMapping("/addresses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Address updateAddress(@RequestBody Address address, @RequestHeader(name = headerName)String header){
        return customerService.updateAddress(address, jwtService.getIdFromAuthHeader(header));
    }

    @PutMapping("/carts/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<Cart> addToCart(@RequestHeader(name = headerName)String header, @PathVariable Long productId) {
        return customerService.addItemToCart(jwtService.getIdFromAuthHeader(header), productId);
    }

    @DeleteMapping("/carts/{itemId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<Cart> removeFromCart(@RequestHeader(name = headerName)String header, @PathVariable Long itemId) {
        return customerService.removeItemFromCart(jwtService.getIdFromAuthHeader(header), itemId);
    }

    @GetMapping("/carts")
    public EntityModel<Cart> readShoppingCart(@RequestHeader(name = headerName)String header) {
        return customerService.readCart(jwtService.getIdFromAuthHeader(header));
    }

    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<CustomerOrder> confirmCart(@RequestHeader(name = headerName)String header) {
        return customerService.CartToOrder(jwtService.getIdFromAuthHeader(header));
    }

    @DeleteMapping("/carts")
    @ResponseStatus(HttpStatus.ACCEPTED)
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
