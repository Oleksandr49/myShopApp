package shopApp.service.customer.cart;

import shopApp.model.user.customer.Cart;

public interface CartService {

    Cart readCart(Long customerId);
    void emptyCart(Long customerId);
    void addItemToCart(Long customerId, Long productId);
    void removeItemFromCart(Long customerId, Long productId);
}
