package com.example.firstproject.model.user.customer;

import com.example.firstproject.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Customer extends User {

    public Customer(){
        super();
    }

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private final Details details = new Details();

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private final ShoppingCart shoppingCart = new ShoppingCart();

    public Details getDetails() {
        return details;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
