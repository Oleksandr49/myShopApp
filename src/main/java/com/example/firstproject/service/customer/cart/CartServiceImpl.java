package com.example.firstproject.service.customer.cart;

import com.example.firstproject.controller.customer.CustomerController;
import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.user.customer.Cart;
import com.example.firstproject.repository.CartRepository;
import com.example.firstproject.service.product.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemService itemService;

    @Override
    public void emptyCart(Cart cart) {
        deleteCartItems(cart.getCartItems());
        cart.getCartItems().clear();
        updateCartTotal(cart);
    }

    @Override
    public void addItemToCart(Cart cart, Long productId) {
        itemService.addItemToCart(cart, productId);
        updateCartTotal(cart);
    }

    @Override
    public void removeItemFromCart(Cart cart, Long itemId) {
        itemService.delete(itemId);
        updateCartTotal(cart);
    }

    @Override
    public EntityModel<Cart> toModel(Cart entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).readShoppingCart("")).withSelfRel(),
                linkTo(methodOn(CustomerController.class).confirmCart("")).withRel("OrderCart"),
                linkTo(methodOn(CustomerController.class).emptyCart("")).withRel("EmptyCart"));
    }

    private void updateCartTotal(Cart cart){
        cart.setTotalCost(calculateCartItems(cart));
        cartRepository.save(cart);
    }

    private void deleteCartItems(List<CartItem> cartItems){
        for (CartItem cartItem : cartItems){
            itemService.delete(cartItem.getId());
        }
    }

    private Double calculateCartItems(Cart cart){
        Double totalCost = 0.00;
        for(Item item : cart.getCartItems()){
            totalCost += item.getCost();
        }
        return totalCost;
    }
}
