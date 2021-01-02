package eCommerce.myShopApplication.repository;

import eCommerce.myShopApplication.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
