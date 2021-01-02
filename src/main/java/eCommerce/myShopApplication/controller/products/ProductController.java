package eCommerce.myShopApplication.controller.products;

import eCommerce.myShopApplication.model.product.Product;
import eCommerce.myShopApplication.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/products")
    public ResponseEntity<?> readAll() {
        return new ResponseEntity<>(productService.readAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<?> readProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.read(id), HttpStatus.OK);

    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> create(@RequestBody Product product) {
        return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Long id){
        return new ResponseEntity<>(productService.update(product, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return productService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
