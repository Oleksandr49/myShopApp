package shopApp.service.customer.cart;

import shopApp.model.user.customer.Cart;

public interface CartService {

    Cart readCart(Long customerId);
    void emptyCart(Long customerId);
    void addProductToCart(Long customerId, Long productId);
    void changeItemAmount(Long customerId, Long itemId, Integer amount);
    void removeItemFromCart(Long customerId, Long productId);
    void updateCartTotal(Long customerId);

    Integer calculateCartTotalCost(Cart cart);
}
