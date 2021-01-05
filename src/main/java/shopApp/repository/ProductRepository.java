package shopApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
