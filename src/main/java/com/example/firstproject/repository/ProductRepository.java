package com.example.firstproject.repository;

import com.example.firstproject.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

}
