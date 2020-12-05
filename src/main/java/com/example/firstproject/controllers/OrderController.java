package com.example.firstproject.controllers;

import com.example.firstproject.model.orders.CustomerOrder;
import com.example.firstproject.model.orders.OrderItem;
import com.example.firstproject.model.product.Product;
import com.example.firstproject.service.jwt.JwtUtil;
import com.example.firstproject.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping(value = "/Orders/history")
    public ResponseEntity<List<CustomerOrder>> readAll(HttpServletRequest httpServletRequest) {
        final List<CustomerOrder> customerOrders = orderService.readAllProducts(jwtUtil.getIdFromToken(httpServletRequest));

        return customerOrders != null && !customerOrders.isEmpty()
                ? new ResponseEntity<>(customerOrders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/Orders")
    public CustomerOrder create(HttpServletRequest httpServletRequest, @RequestBody List<OrderItem> orderItemList) {
        return orderService.create(orderItemList, jwtUtil.getIdFromToken(httpServletRequest));
    }
/*
    @PutMapping(value = "/Orders")
    public Product update(@RequestBody Product product, @PathVariable Integer id){
        return productService.update(product, id);
    }
*/
    @DeleteMapping(value = "/Orders")
    public ResponseEntity<?> delete(HttpServletRequest httpServletRequest) {
        return orderService.delete(jwtUtil.getIdFromToken(httpServletRequest))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
