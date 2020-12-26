package com.example.firstproject.model.item;

import com.example.firstproject.model.user.customer.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CartItem extends Item {
    @ManyToOne
    @JoinColumn(name = "cart_fk")
    private Cart cart;

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @JsonIgnore
    public Cart getCart() {
        return cart;
    }
}
