package shopApp.service.product.item;

import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.user.customer.Cart;

public interface ItemService {

    Item saveItem(Item item);
    void delete (Long id);
    CartItem addItemToCart(Cart cart, Long productId);

}
