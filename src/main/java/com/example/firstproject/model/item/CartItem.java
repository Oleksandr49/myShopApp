package com.example.firstproject.model.item;

import com.example.firstproject.model.user.customer.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CartItem extends Item {
    @ManyToOne
    @JoinColumn(name = "shoppingCart_fk")
    private ShoppingCart shoppingCart;

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @JsonIgnore
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
