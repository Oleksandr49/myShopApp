package com.example.firstproject.service.customer.order;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface OrderService extends RepresentationModelAssembler<CustomerOrder, EntityModel<CustomerOrder>> {

    CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Customer customer);
    EntityModel<CustomerOrder> assembleOrder(Customer customer);
    EntityModel<CustomerOrder> readOrder(Customer customer, Long orderId);
}
