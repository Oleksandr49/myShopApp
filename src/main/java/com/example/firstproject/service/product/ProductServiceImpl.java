package com.example.firstproject.service.product;

import com.example.firstproject.model.product.Product;
import com.example.firstproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> readAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product read(Integer id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product update (Product newProduct, Integer id){
        return productRepository.findById(id)
       .map(product -> {
           product.setProductName(newProduct.getProductName());
           product.setProductPrice(newProduct.getProductPrice());
           return productRepository.save(product);
       })
       .orElseGet(()->{
           newProduct.setId(id);
           return productRepository.save(newProduct);
       });
    }

    @Override
    public Boolean delete(Integer id) {
        if(!productRepository.existsById(id)){
           return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}
