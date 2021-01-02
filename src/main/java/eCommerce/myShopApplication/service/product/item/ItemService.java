package eCommerce.myShopApplication.service.product.item;

import eCommerce.myShopApplication.model.item.Item;
import eCommerce.myShopApplication.model.user.customer.Cart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface ItemService extends RepresentationModelAssembler <Item, EntityModel<Item>> {

    void saveItem(Item item);
    void delete (Long id);
    void addItemToCart(Cart cart, Long productId);

}
