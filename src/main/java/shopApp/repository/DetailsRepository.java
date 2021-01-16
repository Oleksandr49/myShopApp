package shopApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.user.customer.Details;

public interface DetailsRepository extends JpaRepository<Details, Long> {
}
