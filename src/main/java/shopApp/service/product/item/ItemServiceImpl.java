package shopApp.service.product.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.product.Product;
import shopApp.repository.ItemRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    final private ItemRepository itemRepository;

    @Override
    public Item getItem(Long itemId){
        return itemRepository.findById(itemId).orElseThrow();
    }

    @Override
    public Item saveItem(Item item) {
       return itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        if(!itemRepository.existsById(id)){
            throw new EntityNotFoundException("No Such Item");
        }
        itemRepository.deleteById(id);
    }

    @Override
    public CartItem createCartItem(Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setAmount(1);
        cartItem.setCost(cartItem.getAmount() * product.getProductPrice());
        return cartItem;
    }

    @Override
    public void changeItemAmount(Long itemId, Integer newAmount) {
        Item item = getItem(itemId);
        item.setAmount(newAmount);
        saveItem(item);
    }
}
