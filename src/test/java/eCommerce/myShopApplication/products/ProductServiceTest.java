package eCommerce.myShopApplication.products;


import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;
import shopApp.service.product.ProductService;
import shopApp.service.product.ProductServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {


    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    private final ProductService productService = new ProductServiceImpl(productRepository);

    private final Map<Long, Product> mockDB = new HashMap<>();

    @BeforeEach
    public void initDB(){
        for(int i = 0; i < 10; i++){
            String name = "something " + i;
            Long id = Long.valueOf(i);
            Integer price = (i + 2) * 100;
            Product product = new Product();
            product.setProductName(name);
            product.setProductPrice(price);
            product.setId(id);
            mockDB.put(product.getId(), product);
        }
    }


    @Test
    public void productCreatedCorrectly() {
        Product product = new Product();
        product.setProductPrice(20);
        product.setProductName("some");
        when(productService.create(product)).thenReturn(product);
        Assert.assertEquals(productService.create(product), product);
    }
    @Test
    public void readAllProductsCorrect() {
        Exception exception = assertThrows(EntityNotFoundException.class, productService::readAllProducts);
            String expectedMessage = "No Products in database";
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void readProductCorrect() {

    }
    @Test
    public void updateProductCorrect() {

    }
    @Test
    public void deleteProductCorrect() {

    }
}
