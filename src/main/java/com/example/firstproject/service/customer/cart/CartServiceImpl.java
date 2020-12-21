package com.example.firstproject.service.customer.cart;

import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.service.customer.order.OrderService;
import com.example.firstproject.service.product.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Override
    public ShoppingCart readShoppingCart(Customer customer) {
        return customer.getShoppingCart();
    }

    @Override
    public void clearCart(Customer customer) {
        itemService.clearCart(customer.getShoppingCart());
        customer.getShoppingCart().getCartItems().clear();
        customer.getShoppingCart().setTotalCost(0.00);
    }

    @Override
    public CustomerOrder cartToOrder(Customer customer) {
       CustomerOrder customerOrder = orderService.assembleOrder(customer);
       clearCart(customer);
       return customerOrder;
    }

    @Override
    public void addItemToCart(Customer customer, Long productId) {
        Item item = itemService.createCartItem(productId);
        CartItem cartItem = (CartItem) item;
        cartItem.setShoppingCart(customer.getShoppingCart());
        itemService.saveItem(cartItem);
        //calculate new totalCost of Cart
    }

    @Override
    public void removeItemFromCart(Long itemId) {
        itemService.delete(itemId);
        //calculate new totalCost of Cart
    }

}
