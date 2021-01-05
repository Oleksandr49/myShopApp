package shopApp.service.product.item;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import shopApp.model.item.Item;
import shopApp.model.user.customer.Cart;

public interface ItemService extends RepresentationModelAssembler <Item, EntityModel<Item>> {

    void saveItem(Item item);
    void delete (Long id);
    void addItemToCart(Cart cart, Long productId);

}
