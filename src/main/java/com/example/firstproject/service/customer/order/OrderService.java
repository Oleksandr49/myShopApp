package com.example.firstproject.service.customer.order;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Customer;

public interface OrderService {
    CustomerOrder assembleOrder(Customer customer);
}
