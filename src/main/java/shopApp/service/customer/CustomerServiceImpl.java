package shopApp.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Customer;
import shopApp.repository.CustomerRepository;
import shopApp.service.customer.order.OrderService;
import shopApp.service.user.UserService;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    final private CustomerRepository customerRepository;
    final private UserService userService;
    final private OrderService orderService;

    @Override
    public void create(Customer customer) throws EntityExistsException {
        if(customerExists(customer)){
            throw new EntityExistsException("Customer already exists");
        }
            userService.create(customer);
    }

    @Override
    public Customer getCustomer(Long customerId) throws EntityExistsException {
        return customerRepository.findById(customerId).orElseThrow();
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
    public EntityModel<CustomerOrder> CartToOrder(Long id) {
        return null;
    }
/*
    @Override
    public EntityModel<CustomerOrder> CartToOrder(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        EntityModel<CustomerOrder> customerOrder = orderService.assembleOrder(customer);
        cartService.emptyCart(customer.getCart());
        return customerOrder;
    }

 */

    private Boolean customerExists(Customer customer){
        return customerRepository.findUsersByUsername(customer.getUsername()) != null;
    }

}
