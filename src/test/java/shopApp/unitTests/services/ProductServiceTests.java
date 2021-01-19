package shopApp.unitTests.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Product product = new Product();
        product.setProductPrice(100000);
        product.setProductName("PC");
        entityManager.persist(product);
        entityManager.flush();

        // when
        Product found = productRepository.findByProductName(product.getProductName());

        // then
        assertThat(found.getProductName())
                .isEqualTo(product.getProductName());
    }

}
