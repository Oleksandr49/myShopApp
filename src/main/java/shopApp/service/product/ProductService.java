package shopApp.service.product;

import shopApp.model.product.Product;

import javax.persistence.PersistenceException;
import java.util.List;

public interface ProductService {

     List<Product> readAllProducts() throws PersistenceException;
     Product create (Product product);
     Product read (Long id);
     Product update (Product product, Long id);
     Product delete (Long id);
}
