package shopApp.service.product.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.user.customer.Cart;
import shopApp.repository.ItemRepository;
import shopApp.service.product.ProductService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    final private ItemRepository itemRepository;

    final private ProductService productService;

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        if(!itemRepository.existsById(id)){
            throw new EntityNotFoundException("No Such Item");
        }
        itemRepository.deleteById(id);
    }

    @Override
    public void addItemToCart(Cart cart, Long productId) {
        CartItem cartItem = (CartItem) createCartItem(productId);
        cartItem.setCart(cart);
        saveItem(cartItem);
    }

    private Item createCartItem(Long productId) {
        Item item = new CartItem();
        item.setProductId(productId);
        item.setAmount(1);
        item.setCost(item.getAmount() * getPrice(productId));
        return item;
    }

    private Integer getPrice(Long productId){
       return productService.read(productId).getProductPrice();
    }

}
