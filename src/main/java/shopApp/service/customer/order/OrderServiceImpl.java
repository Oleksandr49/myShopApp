package shopApp.service.customer.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.order.CustomerOrder;
import shopApp.model.order.OrderState;
import shopApp.model.user.customer.Cart;
import shopApp.model.user.customer.Customer;
import shopApp.model.user.customer.Details;
import shopApp.repository.OrderRepository;
import shopApp.service.customer.CustomerService;
import shopApp.service.customer.cart.CartService;
import shopApp.service.product.item.ItemService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    final private OrderRepository orderRepository;

    final private ItemService itemService;
    final private CustomerService customerService;
    final private CartService cartService;


    @Override
    public CustomerOrder readOrder(Long customerId, Long orderId) {
        return findOrder(getOrderHistory(customerId), orderId);
    }

    @Override
    public List<CustomerOrder> getOrderHistory(Long customerId) {
        return customerService.getCustomer(customerId).getDetails().getOrderHistory();
    }

    @Override
    public CustomerOrder orderCart(Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if(isCartEmpty(customer.getCart())) throw new IllegalArgumentException("Cart is empty!");
        return assembleOrderForCart(customer);
    }

    private CustomerOrder assembleOrderForCart(Customer customer) {
        Cart cart = customer.getCart();
        Details details = customer.getDetails();
        CustomerOrder customerOrder = createOrder(details);
        customerOrder.setTotalCost(cartService.calculateCartTotalCost(cart));
        orderRepository.save(customerOrder);
        customerOrder =  itemService.moveItemsFromCartToOrder(cart, customerOrder);
        cart.getCartItems().clear();
        cartService.saveCart(cart);
        cartService.updateCartTotal(customer.getId());
        return customerOrder;
    }

    private CustomerOrder createOrder(Details details){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreated(LocalDateTime.now());
        customerOrder.setOrderState(OrderState.STATE);
        customerOrder.setDetails(details);
        return customerOrder;
    }

    private CustomerOrder findOrder(List<CustomerOrder> orderHistory, Long orderId){
        for(CustomerOrder order : orderHistory){
            if(order.getOrderId().equals(orderId)){
                return order;
            }
        }
        throw new EntityNotFoundException("No such Order");
    }

    private Boolean isCartEmpty(Cart cart){
        return cart.getCartItems().isEmpty();
    }
}
