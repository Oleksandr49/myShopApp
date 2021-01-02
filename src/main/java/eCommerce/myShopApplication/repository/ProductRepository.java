package eCommerce.myShopApplication.repository;


import eCommerce.myShopApplication.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
