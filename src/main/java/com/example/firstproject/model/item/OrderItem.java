package com.example.firstproject.model.item;

import com.example.firstproject.model.order.CustomerOrder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem extends Item {
    @ManyToOne
    @JoinColumn(name = "customerOrder_fk")
    private CustomerOrder customerOrder;

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

}
