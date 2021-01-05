package shopApp.service.customer.cart;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import shopApp.model.user.customer.Cart;

public interface CartService extends RepresentationModelAssembler<Cart, EntityModel<Cart>> {

    void emptyCart(Cart cart);
    void addItemToCart(Cart cart, Long productId);
    void removeItemFromCart(Cart cart, Long productId);
}
