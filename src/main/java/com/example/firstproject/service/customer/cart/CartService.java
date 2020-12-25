package com.example.firstproject.service.customer.cart;

import com.example.firstproject.model.user.customer.ShoppingCart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface CartService extends RepresentationModelAssembler<ShoppingCart, EntityModel<ShoppingCart>> {

    void emptyCart(ShoppingCart shoppingCart);
    void addItemToCart(ShoppingCart shoppingCart, Long productId);
    void removeItemFromCart(ShoppingCart shoppingCart, Long productId);
}
