package com.example.firstproject.service.product;

import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.product.Product;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.CustomerRepository;
import com.example.firstproject.repository.ItemRepository;
import com.example.firstproject.repository.ProductRepository;
import com.example.firstproject.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Product> readAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product read(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product update (Product newProduct, Long id){
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
    public CartItem addToCart(Long id, Long productId) {
        Customer customer = customerRepository.getOne(id);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setAmount(1);
        cartItem.setShoppingCart(shoppingCart);
        itemRepository.save(cartItem);
        if(shoppingCart.getTotalCost() != null){
            shoppingCart.setTotalCost(shoppingCart.getTotalCost() + productRepository.getOne(productId).getProductPrice());
        }
        else{
            shoppingCart.setTotalCost(productRepository.getOne(productId).getProductPrice());
        }
        customerRepository.save(customer);
        return cartItem;
    }

    @Override
    public Boolean removeFromCart(Long customerId, Long itemId) {
        Long productId = itemRepository.getOne(itemId).getProductId();
        Customer customer = customerRepository.getOne(customerId);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        itemRepository.deleteById(itemId);
        shoppingCart.setTotalCost(shoppingCart.getTotalCost() - productRepository.getOne(productId).getProductPrice());
        customerRepository.save(customer);
        return true;
    }

    @Override
    public Boolean delete(Long id) {
        if(!productRepository.existsById(id)){
           return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}
