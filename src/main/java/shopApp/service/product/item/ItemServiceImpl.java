package shopApp.service.product.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.item.OrderItem;
import shopApp.model.order.CustomerOrder;
import shopApp.model.product.Product;
import shopApp.model.user.customer.Cart;
import shopApp.repository.ItemRepository;

import java.util.NoSuchElementException;

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
    public CustomerOrder moveItemsFromCartToOrder(Cart cart, CustomerOrder customerOrder) {
        cart.getCartItems().forEach(cartItem -> {
            OrderItem item = createOrderItemFromCartItem(cartItem);
            item.setCustomerOrder(customerOrder);
            itemRepository.save(item);
            itemRepository.deleteById(cartItem.getId());
            customerOrder.getOrderItems().add(item);
        });
        cart.getCartItems().clear();
        return customerOrder;
    }

    @Override
    public Item updateItemAmountAndCost(Long itemId, Integer amount) {
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
            throw new NoSuchElementException("No Item with this ID");
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

    private OrderItem createOrderItemFromCartItem(CartItem cartItem){
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setAmount(cartItem.getAmount());
        orderItem.setCost(cartItem.getCost());
        return orderItem;
    }

    private Integer calculateItemCost(Item item){
        return item.getAmount() * item.getProduct().getProductPrice();
    }
}
