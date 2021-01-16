package shopApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.order.CustomerOrder;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
