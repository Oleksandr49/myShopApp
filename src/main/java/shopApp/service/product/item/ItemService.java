package shopApp.service.product.item;

import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.order.CustomerOrder;
import shopApp.model.product.Product;
import shopApp.model.user.customer.Cart;

public interface ItemService {

    Item getItem(Long itemId);

    Item saveItem(Item item);

    CustomerOrder moveItemsFromCartToOrder(Cart cart, CustomerOrder customerOrder);

    Item updateItemAmount(Long itemId, Integer amount);

    void delete (Long id);
    CartItem createCartItem(Product product);
}
