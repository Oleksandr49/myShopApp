package shopApp.service.product.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import shopApp.controller.customer.CustomerController;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.user.customer.Cart;
import shopApp.repository.ItemRepository;
import shopApp.service.product.ProductService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ItemServiceImpl implements ItemService{

    final private ItemRepository itemRepository;

    final private ProductService productService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ProductService productService) {
        this.itemRepository = itemRepository;
        this.productService = productService;
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
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
       return productService.getProduct(productId).getProductPrice();
    }

    @Override
    public EntityModel<Item> toModel(Item entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).readShoppingCart("")).withRel("Cart"),
                linkTo(methodOn(CustomerController.class).removeFromCart("", entity.getId())).withRel("removeFromCart"));
    }
}
