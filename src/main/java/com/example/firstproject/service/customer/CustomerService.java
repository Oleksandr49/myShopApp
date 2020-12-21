package com.example.firstproject.service.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;

public interface CustomerService {

    void create (Customer customer);

    Details readDetails(Long id);
    void updateDetails(Details details, Long id);
    void deleteDetails(Long id);

    void createAddress(Address address, Long id);
    Address readAddress (Long id);
    void updateAddress (Address address, Long id);
    void deleteAddress (Long id);

    ShoppingCart readCart(Long id);
    void clearCart(Long id);
    CustomerOrder orderCart(Long id);
    void addItemToCart(Long id, Long productId);
}
