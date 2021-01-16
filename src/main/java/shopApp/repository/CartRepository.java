package shopApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.user.customer.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
