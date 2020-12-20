package com.example.firstproject.model.order;

import com.example.firstproject.model.product.Product;


public class OrderPosition {
    private Long id;
    private Product product;
    private Integer amount;

    public OrderPosition(Integer amount, Product product){
        this.amount = amount;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
