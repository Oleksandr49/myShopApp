package shopApp.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of CRUD operations methods from ProductService.
 * Uses ProductRepository to manage database queries.
 * @see ProductRepository
 * @see ProductService
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    /**
     * {@inheritDoc}
     * @see ProductService
     */
    @Override
    public List<Product> readAllProducts(){
        return productRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * @see ProductService
     */
    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     * @throws NoSuchElementException in case when no Product with specified ID can be found in database.
     * @see ProductService
     */
    @Override
    public Product read(Long id) {
        return productRepository.findById(id)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     * @throws NoSuchElementException in case when no Product with specified ID can be found in database.
     * @see ProductService
     */
    @Override
    public Product update (Product newProduct, Long id){
        Product product = read(id);
        product.setProductName(newProduct.getProductName());
        product.setProductPrice(newProduct.getProductPrice());
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     * @throws NoSuchElementException in case when no Product with specified ID can be found in database.
     * @see ProductService
     */
    @Override
    public void delete(Long id) {
        if(!productRepository.existsById(id)){
           throw new NoSuchElementException("No Product with this ID");
        }
        productRepository.deleteById(id);
    }
}
