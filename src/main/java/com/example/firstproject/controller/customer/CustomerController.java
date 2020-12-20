package com.example.firstproject.controller.customer;

import com.example.firstproject.model.item.ItemEntityModelAssembler;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.order.OrderPosition;
import com.example.firstproject.model.order.OrderPositionEntityModelAssembler;
import com.example.firstproject.model.user.customer.*;
import com.example.firstproject.service.customer.CustomerService;
import com.example.firstproject.service.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final String headerName = "Authorization";

    @Autowired
    CustomerService customerService;

    @Autowired
    DetailsEntityModelAssembler detailsEntityModelAssembler;

    @Autowired
    ItemEntityModelAssembler itemEntityModelAssembler;

    @Autowired
    OrderPositionEntityModelAssembler orderPositionEntityModelAssembler;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/customers")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        customerService.create(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/customers/details")
    public ResponseEntity<?> read(@RequestHeader(name = headerName)String header) {
        Details details = customerService.read(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @PutMapping(value = "/customers/details")
    public EntityModel<Details> update(@RequestBody Details details, @RequestHeader(name = headerName)String header){
        Details newDetails = customerService.update(details, jwtUtil.getIdFromAuthHeader(header));
        return detailsEntityModelAssembler.toModel(newDetails);
    }

    @DeleteMapping(value = "/customers/details")
    public ResponseEntity<?> delete(@RequestHeader(name = headerName)String header){
        Boolean result = customerService.delete(jwtUtil.getIdFromAuthHeader(header));
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
        Address newAddress = customerService.updateAddress(address, jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(newAddress, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/details/address")
    public ResponseEntity<?> deleteAddress(@RequestHeader(name = headerName)String header){
        Boolean result = customerService.deleteAddress(jwtUtil.getIdFromAuthHeader(header));
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/customers/shoppingCart")
    public ResponseEntity<?> readShoppingCart(@RequestHeader(name = headerName)String header) {
        ShoppingCart shoppingCart = customerService.readShoppingCart(jwtUtil.getIdFromAuthHeader(header));
        List<OrderPosition> positions = shoppingCart.getCartItems().stream()
                .map(customerService::createOrderPosition)
                .collect(Collectors.toList());
        List<EntityModel<OrderPosition>> prepared = positions.stream()
                .map(orderPositionEntityModelAssembler::toModel)
                .collect(Collectors.toList());
        return new ResponseEntity<>(prepared, HttpStatus.OK);
    }

    @PostMapping(value = "/customers/shoppingCart")
    public ResponseEntity<?> confirmCart(@RequestHeader(name = headerName)String header) {
        CustomerOrder customerOrder = customerService.confirmCart(jwtUtil.getIdFromAuthHeader(header));
        return new ResponseEntity<>(customerOrder, HttpStatus.OK);
    }
}
