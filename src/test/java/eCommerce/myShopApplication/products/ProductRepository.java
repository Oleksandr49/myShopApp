package eCommerce.myShopApplication.products;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopApp.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
