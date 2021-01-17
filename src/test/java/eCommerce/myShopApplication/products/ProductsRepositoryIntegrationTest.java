package eCommerce.myShopApplication.products;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import shopApp.model.product.Product;

@DataJpaTest
@SpringBootTest
public class ProductsRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();

        // when
       // Product found = productRepository.findById(product.getId());

        // then
        //assertThat(found.getId())
           //     .isEqualTo(product.getId());
    }
}
