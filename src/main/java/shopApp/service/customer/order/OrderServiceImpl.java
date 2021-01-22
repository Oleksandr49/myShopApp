package shopApp.service.customer.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.order.CustomerOrder;
import shopApp.model.order.OrderState;
import shopApp.model.user.customer.Customer;
import shopApp.repository.OrderRepository;
import shopApp.service.customer.CustomerService;
import shopApp.service.customer.cart.CartService;
import shopApp.service.product.item.ItemService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    final private OrderRepository orderRepository;

    final private ItemService itemService;
    final private CustomerService customerService;
    final private CartService cartService;


    @Override
    public CustomerOrder readOrder(Long customerId, Long orderId) {
        Customer customer = customerService.getCustomer(customerId);
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow();
        if(customer.getDetails().getId().equals(order.getDetails().getId())) return order;
        else throw new NoSuchElementException("No order with this ID for current customer");
    }

    @Override
    public List<CustomerOrder> getOrderHistory(Long customerId) {
        return customerService.getCustomer(customerId).getDetails().getOrderHistory();
    }

    @Override
    public CustomerOrder orderCart(Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if(customer.getCart().getCartItems().isEmpty()) throw new EntityNotFoundException("No Products in cart");
        return assembleOrderForCart(customer);
    }

    private CustomerOrder assembleOrderForCart(Customer customer) {
        CustomerOrder customerOrder = createOrder(customer);
        customerOrder = itemService.moveItemsFromCartToOrder(customer.getCart(), customerOrder);
        cartService.updateCartTotal(customer.getId());
        return customerOrder;
    }

    private CustomerOrder createOrder(Customer customer){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreated(LocalDateTime.now());
        customerOrder.setOrderState(OrderState.STATE);
        customerOrder.setDetails(customer.getDetails());
        customerOrder.setTotalCost(cartService.calculateCartTotalCost(customer.getCart()));
        orderRepository.save(customerOrder);
        return customerOrder;
    }
}
