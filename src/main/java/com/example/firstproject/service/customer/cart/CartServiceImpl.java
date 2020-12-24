package com.example.firstproject.service.customer.cart;

import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.CartRepository;
import com.example.firstproject.service.product.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemService itemService;

    @Override
    public void clearCart(ShoppingCart shoppingCart) {
        deleteCartItems(shoppingCart.getCartItems());
        updateCartTotal(shoppingCart);
    }

    @Override
    public void addItemToCart(ShoppingCart shoppingCart, Long productId) {
        CartItem cartItem = (CartItem)itemService.createCartItem(productId);
        cartItem.setShoppingCart(shoppingCart);
        itemService.saveItem(cartItem);
        updateCartTotal(shoppingCart);
    }

    @Override
    public void removeItemFromCart(ShoppingCart shoppingCart, Long itemId) {
        itemService.delete(itemId);
        updateCartTotal(shoppingCart);
    }

    private void updateCartTotal(ShoppingCart shoppingCart){
        shoppingCart.setTotalCost(calculateCartItems(shoppingCart));
        cartRepository.save(shoppingCart);
    }

    private void deleteCartItems(List<CartItem> cartItems){
        for (CartItem cartItem : cartItems){
            itemService.delete(cartItem.getId());
        }
    }

    private Double calculateCartItems(ShoppingCart shoppingCart){
        Double totalCost = 0.00;
        for(Item item : shoppingCart.getCartItems()){
            totalCost += item.getCost();
        }
        return totalCost;
    }

}
