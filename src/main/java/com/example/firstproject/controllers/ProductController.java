package com.example.firstproject.controllers;

import com.example.firstproject.model.product.Product;
import com.example.firstproject.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> readAll() {
        final List<Product> products = productService.readAllProducts();

        return products != null && !products.isEmpty()
                ? new ResponseEntity<>(products, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> readProductById(@PathVariable Integer id) {
        final Product product = productService.read(id);

        return product != null
                ? new ResponseEntity<>(product, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/products/create")
    public ResponseEntity<?> create(@RequestBody Product product) {
        productService.create(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/update/{id}")
    public Product update(@RequestBody Product product, @PathVariable Integer id){
        return productService.update(product, id);
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        return productService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
