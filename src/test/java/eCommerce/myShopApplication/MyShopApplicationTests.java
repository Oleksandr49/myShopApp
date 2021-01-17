package eCommerce.myShopApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopApp.MyShopApplication;
import shopApp.controller.products.ProductController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {MyShopApplication.class})
public class MyShopApplicationTests {

    @Autowired
    private ProductController productController;

    @Test
    public void contextLoads() {
        assertThat(productController).isNotNull();
    }

}
