package shopApp.service.product;

import org.springframework.stereotype.Service;
import shopApp.model.product.Product;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public interface ProductService {

     List<Product> readAllProducts() throws PersistenceException;
     Product create (Product product);
     Product read (Long id);
     Product update (Product product, Long id);
     void delete (Long id);
     Boolean isInvalid(Product product);
}
