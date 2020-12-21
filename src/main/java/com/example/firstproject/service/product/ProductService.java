package com.example.firstproject.service.product;

import com.example.firstproject.model.product.Product;

import java.util.List;

public interface ProductService {

     List<Product> readAllProducts();
     void create (Product product);
     Product read (Long id);
     Boolean delete (Long id);
     Product update (Product product, Long id);


}
