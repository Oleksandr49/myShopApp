package eCommerce.myShopApplication.repository;

import eCommerce.myShopApplication.model.order.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
