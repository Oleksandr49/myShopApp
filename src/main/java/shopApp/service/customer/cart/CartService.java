package shopApp.service.customer.cart;

import shopApp.model.user.customer.Cart;

public interface CartService {

    Cart readCart(Long customerId);

    void saveCart(Cart cart);

    void emptyCart(Long customerId);
    Long addProductToCart(Long customerId, Long productId);
    void changeItemAmount(Long customerId, Long itemId, Integer amount);//move to itemService without customerId
    void removeItemFromCart(Long customerId, Long productId);
    void updateCartTotal(Long customerId);

    Integer calculateCartTotalCost(Cart cart);
}
