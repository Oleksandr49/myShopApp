package com.example.firstproject.service.order;

import com.example.firstproject.model.orders.CustomerOrder;
import com.example.firstproject.model.orders.OrderItem;

import java.util.List;

public interface OrderService {
     List<CustomerOrder> readAllProducts(Integer id);
     CustomerOrder create (List<OrderItem> orderItems, Integer id);
     CustomerOrder read (Integer id);
     Boolean delete (Integer id);
     CustomerOrder update (CustomerOrder product, Integer id);
}
