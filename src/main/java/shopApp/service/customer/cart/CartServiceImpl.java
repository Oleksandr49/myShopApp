package shopApp.service.customer.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.product.Product;
import shopApp.model.user.customer.Cart;
import shopApp.repository.CartRepository;
import shopApp.service.customer.CustomerService;
import shopApp.service.product.ProductService;
import shopApp.service.product.item.ItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    final private CartRepository cartRepository;

    final private CustomerService customerService;
    final private ItemService itemService;
    final private ProductService productService;

    @Override
    public Cart readCart(Long customerId){
        return cartRepository.findById(customerService.getCustomer(customerId).getCart().getId())
                .orElseThrow();
    }

    @Override
    public void emptyCart(Long customerId) {
        Cart cart = readCart(customerId);
        deleteCartItems(cart.getCartItems());
        cart.getCartItems().clear();
        updateCartTotal(cart);
    }

    @Override
    public void addItemToCart(Long customerId, Long productId) {
        Cart cart = readCart(customerId);
        Product product = productService.read(productId);
        CartItem cartItem = itemService.createCartItem(product);
        cartItem.setCart(cart);
        itemService.saveItem(cartItem);
        updateCartTotal(cart);
    }

    @Override
    public void removeItemFromCart(Long customerId, Long itemId) {
        itemService.delete(itemId);
        updateCartTotal(readCart(customerId));
    }

    private void updateCartTotal(Cart cart){
        cart.setTotalCost(calculateCartTotal(cart));
        cartRepository.save(cart);
    }

    private void deleteCartItems(List<CartItem> cartItems){
        for (CartItem cartItem : cartItems){
            itemService.delete(cartItem.getId());
        }
    }

    private Integer calculateCartTotal(Cart cart){
        Integer totalCost = 0;
        for(Item item : cart.getCartItems()){
            totalCost += item.getCost();
        }
        return totalCost;
    }
}
