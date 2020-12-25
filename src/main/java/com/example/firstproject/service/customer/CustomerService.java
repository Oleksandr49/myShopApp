package com.example.firstproject.service.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface CustomerService {

    void create (Customer customer);

    EntityModel<Details> readDetails(Long id);
    EntityModel<Details> updateDetails(Details details, Long id);

    Address readAddress (Long id);
    Address updateAddress (Address address, Long id);

    EntityModel<ShoppingCart> readCart(Long id);
    EntityModel<ShoppingCart> emptyCart(Long id);
    EntityModel<ShoppingCart> addItemToCart(Long id, Long productId);
    EntityModel<ShoppingCart> removeItemFromCart(Long id, Long productId);

    CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Long id);
    EntityModel<CustomerOrder> readOrder(Long id, Long orderId);
    EntityModel<CustomerOrder> CartToOrder(Long id);
}
