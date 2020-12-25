package com.example.firstproject.service.customer.cart;

import com.example.firstproject.controller.customer.CustomerController;
import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.user.customer.ShoppingCart;
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
    public void emptyCart(ShoppingCart shoppingCart) {
        deleteCartItems(shoppingCart.getCartItems());
        shoppingCart.getCartItems().clear();
        updateCartTotal(shoppingCart);
    }

    @Override
    public void addItemToCart(ShoppingCart shoppingCart, Long productId) {
        itemService.addItemToCart(shoppingCart, productId);
        updateCartTotal(shoppingCart);
    }

    @Override
    public void removeItemFromCart(ShoppingCart shoppingCart, Long itemId) {
        itemService.delete(itemId);
        updateCartTotal(shoppingCart);
    }

    @Override
    public EntityModel<ShoppingCart> toModel(ShoppingCart entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).readShoppingCart("")).withSelfRel(),
                linkTo(methodOn(CustomerController.class).confirmCart("")).withRel("OrderCart"),
                linkTo(methodOn(CustomerController.class).emptyCart("")).withRel("EmptyCart"));
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
