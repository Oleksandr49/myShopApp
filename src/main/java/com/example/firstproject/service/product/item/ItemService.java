package com.example.firstproject.service.product.item;

import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.user.customer.ShoppingCart;

public interface ItemService {

    void saveItem(Item item);
    Item createCartItem (Long productId);
    void delete (Long id);
    void clearCart(ShoppingCart shoppingCart);
}
