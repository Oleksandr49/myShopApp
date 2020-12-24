package com.example.firstproject.controller.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.service.customer.CustomerService;
import com.example.firstproject.service.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final String headerName = "Authorization";

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/customers")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        customerService.create(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/customers/details")
    public ResponseEntity<?> read(@RequestHeader(name = headerName)String header) {
        Details details = customerService.readDetails(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/details")
    public ResponseEntity<?> update(@RequestBody Details details, @RequestHeader(name = headerName)String header){
        customerService.updateDetails(details, jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/details")
    public ResponseEntity<?> delete(@RequestHeader(name = headerName)String header){
        customerService.deleteDetails(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/customers/details/addresses")
    public ResponseEntity<?> readAddress(@RequestHeader(name = headerName)String header) {
        Address address = customerService.readAddress(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping(value = "/customers/details/addresses")
    public ResponseEntity<?> createAddress(@RequestBody Address address, @RequestHeader(name = headerName)String header) {
        customerService.createAddress(address, jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/details/addresses")
    public ResponseEntity<?> updateAddress(@RequestBody Address address, @RequestHeader(name = headerName)String header){
        customerService.updateAddress(address, jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/details/addresses")
    public ResponseEntity<?> deleteAddress(@RequestHeader(name = headerName)String header){
        customerService.deleteAddress(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/customers/shoppingCarts")
    public ResponseEntity<?> readShoppingCart(@RequestHeader(name = headerName)String header) {
        ShoppingCart shoppingCart = customerService.readCart(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @PostMapping(value = "/customers/shoppingCarts")
    public ResponseEntity<?> confirmCart(@RequestHeader(name = headerName)String header) {
        CustomerOrder customerOrder = customerService.CartToOrder(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(customerOrder, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/orders")
    public ResponseEntity<?> readOrderHistory(@RequestHeader(name = headerName)String header) {
        List<CustomerOrder> orderHistory = customerService.getOrderHistory(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/orders/{orderId}")
    public ResponseEntity<?> readOrder(@RequestHeader(name = headerName)String header, @PathVariable Long orderId) {
        CustomerOrder customerOrder = customerService.readOrder(jwtUtil.getIdFromAuthHeader(header), orderId);
        return new ResponseEntity<>(customerOrder, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/carts/{productId}")
    public ResponseEntity<?> addToCart(@RequestHeader(name = headerName)String header, @PathVariable Long productId) {
        customerService.addItemToCart(jwtUtil.getIdFromAuthHeader(header), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/carts/{itemId}")
    public ResponseEntity<?> removeFromCart(@RequestHeader(name = headerName)String header, @PathVariable Long itemId) {
        customerService.removeItemFromCart(jwtUtil.getIdFromAuthHeader(header), itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
