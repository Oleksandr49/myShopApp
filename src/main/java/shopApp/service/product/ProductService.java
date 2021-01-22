package shopApp.service.product;

import org.springframework.stereotype.Service;
import shopApp.model.product.Product;

import java.util.List;

@Service
public interface ProductService {

     List<Product> readAllProducts();
     Product create (Product product);
     Product read (Long productId);
     Product update (Product newProduct, Long productIdd);
     void delete (Long productId);
}
