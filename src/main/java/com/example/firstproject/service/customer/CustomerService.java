package com.example.firstproject.service.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;

import java.util.List;

public interface CustomerService {

    void create (Customer customer);

    Details readDetails(Long id);
    void updateDetails(Details details, Long id);

    Address readAddress (Long id);
    void updateAddress (Address address, Long id);

    ShoppingCart readCart(Long id);
    CustomerOrder CartToOrder(Long id);
    void addItemToCart(Long id, Long productId);
    void removeItemFromCart(Long id, Long productId);

    List<CustomerOrder> getOrderHistory(Long id);
    CustomerOrder readOrder(Long id, Long orderId);
}
