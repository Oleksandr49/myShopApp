package com.example.firstproject.model.user.customer;

import com.example.firstproject.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Customer extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Details details = new Details();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final ShoppingCart shoppingCart = new ShoppingCart();

    public Customer(){
        super();
    }

    public Details getDetails() {
        return details;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
