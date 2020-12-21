package com.example.firstproject.service.customer.cart;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.ShoppingCart;

public interface CartService {

    ShoppingCart readShoppingCart(Customer customer);
    void clearCart(Customer customer);
    CustomerOrder cartToOrder(Customer customer);
    void addItemToCart(Customer customer, Long productId);
    public void calculateCart(Customer customer);
}
