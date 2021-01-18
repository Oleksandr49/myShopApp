package shopApp.service.customer;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Cart;
import shopApp.model.user.customer.Customer;
import shopApp.model.user.customer.Details;

public interface CustomerService {

    void create (Customer customer) throws DataBaseException;

    EntityModel<Details> readDetails(Long id);
    EntityModel<Details> updateDetails(Details details, Long id);

    Address readAddress (Long id);
    Address updateAddress (Address address, Long id);

    EntityModel<Cart> readCart(Long id);
    EntityModel<Cart> emptyCart(Long id);
    EntityModel<Cart> addItemToCart(Long id, Long productId);
    EntityModel<Cart> removeItemFromCart(Long id, Long productId);

    CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Long id);
    EntityModel<CustomerOrder> readOrder(Long id, Long orderId);
    EntityModel<CustomerOrder> CartToOrder(Long id);
}
