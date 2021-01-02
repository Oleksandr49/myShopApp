package eCommerce.myShopApplication.controller.products;

import eCommerce.myShopApplication.model.product.Product;
import eCommerce.myShopApplication.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> readAll() {
        return productService.readAllProducts();
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> readProductById(@PathVariable Long id) {
        return productService.read(id);

    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Product> create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<Product> update(@RequestBody Product product, @PathVariable Long id){
        return productService.update(product, id);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean delete(@PathVariable Long id) {
        return productService.delete(id);
    }
}
