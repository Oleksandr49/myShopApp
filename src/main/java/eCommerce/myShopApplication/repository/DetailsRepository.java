package eCommerce.myShopApplication.repository;

import eCommerce.myShopApplication.model.user.customer.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Long> {
}
