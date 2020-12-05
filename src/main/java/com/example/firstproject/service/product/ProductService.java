package com.example.firstproject.service.product;

import com.example.firstproject.model.product.Product;

import java.util.List;

public interface ProductService {

    public List<Product> readAllProducts();
    public void create (Product product);
    public Product read (Integer id);
    public Boolean delete (Integer id);
    public Product update (Product product, Integer id);

}
