package shopApp.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    @Override
    public List<Product> readAllProducts() throws PersistenceException {
        List<Product> productsList = productRepository.findAll();
        if(productsList.isEmpty()){
            throw new EntityNotFoundException("No Products in database");
        }
        return productsList;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product read(Long id) {
        return productRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public Product update (Product newProduct, Long id){
        if(isInvalid(newProduct)) throw new IllegalArgumentException("Entity is invalid");
        return productRepository.findById(id)
       .map(product -> {
               product.setProductName(newProduct.getProductName());
               product.setProductPrice(newProduct.getProductPrice());
               return productRepository.save(product);
       })
       .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        if(!productRepository.existsById(id)){
           throw new EntityNotFoundException("No Such Product");
        }
        productRepository.deleteById(id);
    }

    public Boolean isInvalid(Product product){
        return product.getProductName() == null || product.getProductPrice() == null || product.getProductPrice() <= 0 || product.getProductName().isBlank();
    }
}
