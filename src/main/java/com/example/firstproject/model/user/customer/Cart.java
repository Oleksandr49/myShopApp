package com.example.firstproject.model.user.customer;

import com.example.firstproject.model.item.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double totalCost = 0.00;
    @OneToMany(mappedBy = "cart")
    private final List<CartItem> cartItems = new ArrayList<>();

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
