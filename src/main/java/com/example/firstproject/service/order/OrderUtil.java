package com.example.firstproject.service.order;

import com.example.firstproject.model.orders.CustomerOrder;
import com.example.firstproject.model.orders.OrderItem;
import com.example.firstproject.model.product.Product;
import com.example.firstproject.repository.ProductRepository;
import com.example.firstproject.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderUtil {

    @Autowired
    ProductService productService;

    public CustomerOrder assembleOrder(List<OrderItem> orderItems){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderItems(orderItems);
        customerOrder.setDate(LocalDateTime.now());
        customerOrder.setTotalCost(this.getOrderTotalCost(orderItems));
        return customerOrder;
    }

    private Double getOrderTotalCost(List<OrderItem> orderItems){
        Double totalCost = 0.0;
        for(OrderItem orderItem : orderItems){
            Product product = productService.read(orderItem.getProductId());
            totalCost += (product.getProductPrice() * orderItem.getProductAmount());
        }
        return totalCost;
    }
}
