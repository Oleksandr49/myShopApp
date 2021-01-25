package shopApp.integrationTests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopApp.model.item.Item;
import shopApp.repository.ItemRepository;
import shopApp.service.product.item.ItemService;
import shopApp.service.product.item.ItemServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ItemServiceTests {

    @Autowired
    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    public void initService(){
        itemService = new ItemServiceImpl(itemRepository);
    }

    @Test
    public void validItemPersistingTest() {
        Item item = new Item();
        item.setAmount(2);
        item.setCost(100);
        assertEquals(item, itemService.saveItem(item));
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

    }
}

