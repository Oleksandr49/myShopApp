package shopApp.controller.products;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.product.Product;
import shopApp.service.product.ProductService;
import shopApp.service.product.ProductWrapper;

import javax.persistence.PersistenceException;


@RestController
@RequiredArgsConstructor
public class ProductController {

    final private ProductService productService;

    final private ProductWrapper productWrapper;

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> readAll() throws PersistenceException {
        return productWrapper.toEntityCollection(productService.readAllProducts());
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> readProductById(@PathVariable Long id) {
        return productWrapper.toModel(productService.read(id));
    }

    @PostMapping("/products")
    public EntityModel<Product> create(@RequestBody Product product) {
        return productWrapper.toModel(productService.create(product));
    }

    @PutMapping("/products/{id}")
    public EntityModel<Product> update(@RequestBody Product product, @PathVariable Long id){
        return productWrapper.toModel(productService.update(product, id));
    }

    @DeleteMapping("/products/{id}")
    public Product delete(@PathVariable Long id) {
        return productService.delete(id);
    }
}
