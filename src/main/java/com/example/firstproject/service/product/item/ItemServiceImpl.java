package com.example.firstproject.service.product.item;

import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemRepository itemRepository;

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item createCartItem(Long productId) {
        Item item = new CartItem();
        item.setProductId(productId);
        item.setAmount(1);
        return item;
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void clearCart(ShoppingCart shoppingCart) {
        for(Item item:shoppingCart.getCartItems()){
            itemRepository.deleteById(item.getId());
        }
    }
}
