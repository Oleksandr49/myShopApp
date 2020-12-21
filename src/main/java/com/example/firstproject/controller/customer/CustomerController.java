package com.example.firstproject.controller.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.*;
import com.example.firstproject.service.customer.CustomerService;
import com.example.firstproject.service.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final String headerName = "Authorization";

    @Autowired
    CustomerService customerService;

    @Autowired
    DetailsEntityModelAssembler detailsEntityModelAssembler;

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

    @GetMapping(value = "/customers/details/address")
    public ResponseEntity<?> readAddress(@RequestHeader(name = headerName)String header) {
        Address address = customerService.readAddress(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping(value = "/customers/details/address")
    public ResponseEntity<?> createAddress(@RequestBody Address address, @RequestHeader(name = headerName)String header) {
        customerService.createAddress(address, jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/details/address")
    public ResponseEntity<?> updateAddress(@RequestBody Address address, @RequestHeader(name = headerName)String header){
        customerService.updateAddress(address, jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/details/address")
    public ResponseEntity<?> deleteAddress(@RequestHeader(name = headerName)String header){
        customerService.deleteAddress(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/customers/shoppingCart")
    public ResponseEntity<?> readShoppingCart(@RequestHeader(name = headerName)String header) {
        ShoppingCart shoppingCart = customerService.readCart(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @PostMapping(value = "/customers/shoppingCart")
    public ResponseEntity<?> confirmCart(@RequestHeader(name = headerName)String header) {
        CustomerOrder customerOrder = customerService.orderCart(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(customerOrder, HttpStatus.OK);
    }
}
