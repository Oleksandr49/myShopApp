package shopApp.integrationTests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;
import shopApp.service.product.ProductService;
import shopApp.service.product.ProductServiceImpl;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProductServiceTests {

    @Autowired
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void initService(){
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void readExistingProductTest(){
        Product product = new Product();
        product.setId(1L);
        product.setProductPrice(100000);
        product.setProductName("Phone");
        assertEquals(product, productService.read(product.getId()));
    }

    @Test
    public void readNonExistingProductTest(){
        assertThrows(NoSuchElementException.class, ()-> productService.read(12931L));
    }

    @Test
    public void readAllProductsTest(){
        assertNotNull(productService.readAllProducts());
        assertFalse(productService.readAllProducts().isEmpty());
    }

    @Test
    public void readAllProductsWhenNoProductsInDatabaseThrowsEntityNotFoundException(){
        ProductServiceImpl productService = new ProductServiceImpl(Mockito.mock(ProductRepository.class));
        assertThrows(EntityNotFoundException.class, productService::readAllProducts);
    }

    @Test
    public void creationOfValidProductTest(){
        Product product = new Product();
        product.setProductName("TestProduct");
        product.setProductPrice(123000);
        assertEquals(product, productService.create(product));
    }

    @Test
    public void creationOfInvalidProductTest(){
        Product product = new Product();
        assertThrows(ConstraintViolationException.class, ()->productService.create(product));
    }

    @Test
    public void updatingOfExistingProductTest(){
        Product product = new Product();
        product.setId(5L);
        product.setProductPrice(88888);
        product.setProductName("Monitor");
        assertEquals(product, productService.update(product, product.getId()));
    }

    @Test
    public void updatingNonExistingProductTest() {
        Product product = new Product();
        product.setId(-1L);
        product.setProductPrice(88888);
        product.setProductName("Monitor");
        assertThrows(NoSuchElementException.class, ()->productService.update(product, product.getId()));
    }

    @Test
    public void updatingOfExistingProductWithInvalidEntityTest() {
        Product product = new Product();
        product.setId(5L);
        product.setProductName("Monitor");
        assertThrows(IllegalArgumentException.class, ()->productService.update(product, product.getId()));
    }

    @Test
    public void deletionOfExistingProduct() {
        productService.delete(1L);
        assertThrows(NoSuchElementException.class, ()->productService.read(1L));
    }

    @Test
    public void deletionOfNonExistingProduct() {
        assertThrows(EntityNotFoundException.class, ()->productService.delete(-1L));
    }
}

