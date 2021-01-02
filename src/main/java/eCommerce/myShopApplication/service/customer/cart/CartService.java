package eCommerce.myShopApplication.service.customer.cart;

import eCommerce.myShopApplication.model.user.customer.Cart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface CartService extends RepresentationModelAssembler<Cart, EntityModel<Cart>> {

    void emptyCart(Cart cart);
    void addItemToCart(Cart cart, Long productId);
    void removeItemFromCart(Cart cart, Long productId);
}
