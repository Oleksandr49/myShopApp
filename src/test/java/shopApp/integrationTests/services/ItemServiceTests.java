package shopApp.integrationTests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopApp.model.item.CartItem;
import shopApp.model.item.Item;
import shopApp.model.product.Product;
import shopApp.model.user.customer.Cart;
import shopApp.repository.ItemRepository;
import shopApp.service.product.ProductService;
import shopApp.service.product.item.ItemService;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ItemServiceTests {

    @Autowired
    private ItemRepository itemRepository;
    private ItemService itemService;
    private final ProductService productService = Mockito.mock(ProductService.class);

    @BeforeEach
    public void initService(){
       // itemService = new ItemServiceImpl(itemRepository, productService);
    }

    @Test
    public void validItemPersistingTest() {
        Item item = new Item();
        item.setAmount(2);
        item.setCost(100);
        assertEquals(item, itemService.saveItem(item));
    }

    @Test
    public void InvalidItemPersistingTest() {
        Item item = new Item();
        assertThrows(ConstraintViolationException.class, ()->itemService.saveItem(item));
    }

    @Test
    public void deletionOfExistingItem() {
        itemService.delete(199L);
        assertThrows(NoSuchElementException.class, ()->itemRepository.findById(199L).orElseThrow());
    }

    @Test
    public void deletionOfNonExistingItem() {
        assertThrows(EntityNotFoundException.class, ()->itemService.delete(-1L));
    }

    @Test
    public void addingValidItemToCart() {
        Cart cart = Mockito.mock(Cart.class);
        cart.setId(5L);
        Product product = Mockito.mock(Product.class);
        product.setId(2L);
        product.setProductName("boots");
        product.setProductPrice(1000);
        CartItem expectedItem = new CartItem();
        expectedItem.setCart(cart);
        when(productService.read(product.getId())).thenReturn(product);
       // assertEquals(expectedItem, itemService.addItemToCart(cart, product.getId()));
    }
}

