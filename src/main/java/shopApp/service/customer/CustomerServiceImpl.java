package shopApp.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.user.customer.Customer;
import shopApp.repository.CustomerRepository;
import shopApp.service.user.UserService;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    final private CustomerRepository customerRepository;
    final private UserService userService;

    @Override
    public void create(Customer customer) throws EntityExistsException {
        /*
        if(customerExists(customer)){
            throw new EntityExistsException("Customer already exists");
        }

         */
            userService.create(customer);
    }

    @Override
    public Customer getCustomer(Long customerId) throws EntityExistsException {
        return customerRepository.findById(customerId).orElseThrow();
    }
/*
    private Boolean customerExists(Customer customer){
        return customerRepository.findUsersByUsername(customer.getUsername()) != null;
    }

 */

}
