package com.example.firstproject.controller.products;

import com.example.firstproject.model.product.Product;
import com.example.firstproject.model.product.ProductEntityModelAssembler;
import com.example.firstproject.service.customer.CustomerService;
import com.example.firstproject.service.customer.cart.CartService;
import com.example.firstproject.service.jwt.JwtUtil;
import com.example.firstproject.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductEntityModelAssembler productEntityModelAssembler;

    @Autowired
    JwtUtil jwtUtil;

    private final String headerName = "Authorization";

    @GetMapping(value = "/products")
    public CollectionModel<EntityModel<Product>> readAll() {
        List<EntityModel<Product>> products = productService.readAllProducts().stream() //
                .map(productEntityModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).readAll()).withSelfRel());
    }

    @GetMapping(value = "/products/{id}")
    public EntityModel<Product> readProductById(@PathVariable Long id) {
        final Product product = productService.read(id);

        return productEntityModelAssembler.toModel(product);

    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> create(@RequestBody Product product) {
        productService.create(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/update/{id}")
    public EntityModel<Product> update(@RequestBody Product product, @PathVariable Long id){
        Product newProduct =  productService.update(product, id);
        return productEntityModelAssembler.toModel(newProduct);
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return productService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/products/cart/{productId}")
    public ResponseEntity<?> addToCart(@RequestHeader(name = headerName)String header, @PathVariable Long productId) {
        customerService.addItemToCart(jwtUtil.getIdFromAuthHeader(header), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/cart/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
