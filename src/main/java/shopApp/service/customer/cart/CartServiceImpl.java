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

import javax.persistence.EntityExistsException;
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
    public void saveCart(Cart cart){
         cartRepository.save(cart);
    }

    @Override
    public void emptyCart(Long customerId) {
        Cart cart = readCart(customerId);
        deleteAllCartItems(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
        updateCartTotal(customerId);
    }

    @Override
    public void addProductToCart(Long customerId, Long productId) {
        Cart cart = readCart(customerId);
        Product product = productService.read(productId);
        if(isProductInCart(cart, product)) throw new EntityExistsException("Product already in cart");
        createAndAddItemToCart(cart, product);
        updateCartTotal(customerId);
    }

    @Override
    public void changeItemAmount(Long customerId, Long itemId, Integer amount){
        itemService.updateItemAmountAndCost(itemId, amount);
        updateCartTotal(customerId);
    }

    @Override
    public void removeItemFromCart(Long customerId, Long itemId) {
        itemService.delete(itemId);
        updateCartTotal(customerId);
    }

    private Boolean isProductInCart(Cart cart, Product product){
        for(CartItem cartItem : cart.getCartItems()){
            if(cartItem.getProduct().getId().equals(product.getId())) return true;
        }
        return false;
    }

    private void createAndAddItemToCart(Cart cart, Product product){
        CartItem cartItem = itemService.createCartItem(product);
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);
        itemService.saveItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartTotal(Long customerId){
        Long cartId = readCart(customerId).getId();
        cartRepository.findById(cartId)
        .map(cart -> {
            cart.setTotalCost(calculateCartTotalCost(cart));
            return cartRepository.save(cart);
        }).orElseThrow();

    }
    @Override
    public Integer calculateCartTotalCost(Cart cart){
        Integer totalCost = 0;
        for(Item item : cart.getCartItems()){
            totalCost += item.getCost();
        }
        return totalCost;
    }

    private void deleteAllCartItems(List<CartItem> cartItems){
        for (CartItem cartItem : cartItems){
            itemService.delete(cartItem.getId());
        }
    }
}
