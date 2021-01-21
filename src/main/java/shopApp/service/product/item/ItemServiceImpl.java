package shopApp.service.product.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.item.OrderItem;
import shopApp.model.order.CustomerOrder;
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
    public OrderItem convertCartItemToOrderItem(Long itemId, CustomerOrder customerOrder) {
        return itemRepository.findById(itemId)
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setAmount(cartItem.getAmount());
                    orderItem.setCost(cartItem.getCost());
                    orderItem.setCustomerOrder(customerOrder);
                    return itemRepository.save(orderItem);
                }).orElseThrow();
    }

    @Override
    public Item updateItemAmount(Long itemId, Integer amount) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    item.setAmount(amount);
                    item.setCost(calculateItemCost(item));
                    return itemRepository.save(item);
                }).orElseThrow();
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
        cartItem.setCost(calculateItemCost(cartItem));
        return cartItem;
    }

    private Integer calculateItemCost(Item item){
        return item.getAmount() * item.getProduct().getProductPrice();
    }
}
