package eCommerce.myShopApplication.repository;

import eCommerce.myShopApplication.model.user.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
