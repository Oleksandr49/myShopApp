package shopApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.product.Product;

/**
 * JPA repository for Product entity.
 * @see JpaRepository
 * @see Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     *
     * @param productName of entity to be found.
     * @return {@link Product} entity found by specified name.
     */
    Product findByProductName(String productName);

}

