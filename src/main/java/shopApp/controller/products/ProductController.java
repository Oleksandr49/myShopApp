package shopApp.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.product.Product;
import shopApp.service.product.ProductService;


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
    public EntityModel<Product> create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/products/{id}")
    public EntityModel<Product> update(@RequestBody Product product, @PathVariable Long id){
        return productService.update(product, id);
    }

    @DeleteMapping("/products/{id}")
    public Boolean delete(@PathVariable Long id) {
        return productService.delete(id);
    }
}
