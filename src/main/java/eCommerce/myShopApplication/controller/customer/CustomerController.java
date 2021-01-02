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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final String headerName = "Authorization";

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtService jwtService;

    @PostMapping(value = "/customers")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        customerService.create(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/customers/details")
    public ResponseEntity<?> readDetails(@RequestHeader(name = headerName)String header) {
        EntityModel<Details> details = customerService.readDetails(jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/details")
    public ResponseEntity<?> updateDetails(@RequestBody Details details, @RequestHeader(name = headerName)String header){
        EntityModel<Details> result = customerService.updateDetails(details, jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/details/addresses")
    public ResponseEntity<?> readAddress(@RequestHeader(name = headerName)String header) {
        Address address = customerService.readAddress(jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/details/addresses")
    public ResponseEntity<?> updateAddress(@RequestBody Address address, @RequestHeader(name = headerName)String header){
        Address result = customerService.updateAddress(address, jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/carts/{productId}")
    public ResponseEntity<?> addToCart(@RequestHeader(name = headerName)String header, @PathVariable Long productId) {
        EntityModel<Cart> cart = customerService.addItemToCart(jwtService.getIdFromAuthHeader(header), productId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/carts/{itemId}")
    public ResponseEntity<?> removeFromCart(@RequestHeader(name = headerName)String header, @PathVariable Long itemId) {
        EntityModel<Cart> cart = customerService.removeItemFromCart(jwtService.getIdFromAuthHeader(header), itemId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/carts")
    public ResponseEntity<?> readShoppingCart(@RequestHeader(name = headerName)String header) {
        EntityModel<Cart> cart = customerService.readCart(jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @PostMapping(value = "/customers/carts")
    public ResponseEntity<?> confirmCart(@RequestHeader(name = headerName)String header) {
        EntityModel<CustomerOrder> customerOrder = customerService.CartToOrder(jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(customerOrder, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/carts")
    public ResponseEntity<?> emptyCart(@RequestHeader(name = headerName)String header) {
        EntityModel<Cart> cart = customerService.emptyCart(jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/orders")
    public ResponseEntity<?> readOrderHistory(@RequestHeader(name = headerName)String header) {
        CollectionModel<EntityModel<CustomerOrder>> orderHistory = customerService.getOrderHistory(jwtService.getIdFromAuthHeader(header));
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/orders/{orderId}")
    public ResponseEntity<?> readOrder(@RequestHeader(name = headerName)String header, @PathVariable Long orderId) {
        EntityModel<CustomerOrder> customerOrder = customerService.readOrder(jwtService.getIdFromAuthHeader(header), orderId);
        return new ResponseEntity<>(customerOrder, HttpStatus.OK);
    }


}
