package com.example.firstproject.service.customer.cart;

import com.example.firstproject.model.user.customer.ShoppingCart;

public interface CartService {

    void clearCart(ShoppingCart shoppingCart);
    void addItemToCart(ShoppingCart shoppingCart, Long productId);
    void removeItemFromCart(ShoppingCart shoppingCart, Long productId);
}
