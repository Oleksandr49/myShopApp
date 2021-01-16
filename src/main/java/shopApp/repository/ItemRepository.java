package shopApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
