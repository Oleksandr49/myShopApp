package shopApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.user.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
