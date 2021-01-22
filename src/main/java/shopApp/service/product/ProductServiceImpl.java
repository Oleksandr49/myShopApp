package shopApp.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    @Override
    public List<Product> readAllProducts(){
        return productRepository.findAll();
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
           throw new NoSuchElementException("No Product with this ID");
        }
        productRepository.deleteById(id);
    }
}
