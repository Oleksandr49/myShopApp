package shopApp.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;
import shopApp.service.product.ProductService;
import shopApp.service.product.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class ProductServiceUnitTests {

    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final ProductService productService = new ProductServiceImpl(productRepository);

    @Test
    public void isProductInvalid() {
        List<Product> invalidProducts = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        product2.setProductPrice(0);
        product2.setProductName("");
        Product product3 = new Product();
        product3.setProductName("    ");
        product3.setProductPrice(-1000);
        invalidProducts.add(product1);
        invalidProducts.add(product2);
        invalidProducts.add(product3);
        for(Product product : invalidProducts){
            assertTrue(productService.isInvalid(product));
        }
    }
}
