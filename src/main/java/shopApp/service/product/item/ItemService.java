package shopApp.service.product.item;

import shopApp.model.item.Item;
import shopApp.model.user.customer.Cart;

public interface ItemService {

    void saveItem(Item item);
    void delete (Long id);
    void addItemToCart(Cart cart, Long productId);

}
