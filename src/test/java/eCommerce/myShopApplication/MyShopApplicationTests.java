package eCommerce.myShopApplication;

import eCommerce.myShopApplication.products.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopApp.MyShopApplication;

@SpringBootTest(classes = {MyShopApplication.class})
public class MyShopApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void contextLoads() {
    }

}
