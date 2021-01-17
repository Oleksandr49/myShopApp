package shopApp.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import shopApp.exceptions.DataException;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Cart;
import shopApp.model.user.customer.Customer;
import shopApp.model.user.customer.Details;
import shopApp.repository.CustomerRepository;
import shopApp.service.customer.cart.CartService;
import shopApp.service.customer.details.DetailsService;
import shopApp.service.customer.order.OrderService;
import shopApp.service.user.UserService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    final private
    CustomerRepository customerRepository;

    final private UserService userService;
    final private DetailsService detailsService;
    final private CartService cartService;
    final private OrderService orderService;

    @Override
    public void create(Customer customer) throws DataException{
        if(customerExists(customer)){
            throw new DataException("Username already exists");
        }
            userService.create(customer);
    }

    @Override
    public EntityModel<Details> readDetails(Long customerId) {
        Details details = customerRepository.getOne(customerId).getDetails();
        return detailsService.toModel(details);
    }

    @Override
    public EntityModel<Details> updateDetails(Details newDetails, Long customerId) {
        Long detailsId = customerRepository.getOne(customerId).getDetails().getId();
        return detailsService.updateDetails(newDetails, detailsId);
    }

    @Override
    public Address readAddress(Long customerId) {
        Address address = customerRepository.getOne(customerId).getDetails().getAddress();
        if(address == null){
            return new Address();
        }
        return address;
    }

    @Override
    public Address updateAddress(Address newAddress, Long customerId) {
        Long detailsId = customerRepository.getOne(customerId).getDetails().getId();
        return detailsService.updateAddress(newAddress, detailsId);
    }

    @Override
    public CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Long customerId) {
        return orderService.getOrderHistory(customerRepository.getOne(customerId));
    }

    @Override
    public EntityModel<CustomerOrder> readOrder(Long customerId, Long orderId) {
        return orderService.readOrder(customerRepository.getOne(customerId), orderId);
    }

    @Override
    public EntityModel<Cart> readCart(Long customerId) {
        return cartService.toModel(customerRepository.getOne(customerId).getCart());
    }

    @Override
    public EntityModel<CustomerOrder> CartToOrder(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        EntityModel<CustomerOrder> customerOrder = orderService.assembleOrder(customer);
        cartService.emptyCart(customer.getCart());
        return customerOrder;
    }

    @Override
    public EntityModel<Cart> emptyCart(Long id) {
        Cart cart = customerRepository.getOne(id).getCart();
        cartService.emptyCart(cart);
        return cartService.toModel(cart);
    }

    @Override
    public EntityModel<Cart> addItemToCart(Long customerId, Long productId) {
        Cart cart = customerRepository.getOne(customerId).getCart();
        cartService.addItemToCart(cart, productId);
        return cartService.toModel(cart);
    }

    @Override
    public EntityModel<Cart> removeItemFromCart(Long customerId, Long itemId) {
        Cart cart = customerRepository.getOne(customerId).getCart();
        cartService.removeItemFromCart(cart, itemId);
        return cartService.toModel(cart);
    }

    private Boolean customerExists(Customer customer){
        return customerRepository.findUsersByUsername(customer.getUsername()) != null;
    }

}
