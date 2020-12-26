package com.example.firstproject.service.product.item;

import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.user.customer.Cart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface ItemService extends RepresentationModelAssembler <Item, EntityModel<Item>> {

    void saveItem(Item item);
    void delete (Long id);
    void addItemToCart(Cart cart, Long productId);

}
