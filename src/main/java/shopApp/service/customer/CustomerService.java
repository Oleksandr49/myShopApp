package shopApp.service.customer;

import shopApp.model.user.customer.Customer;

import javax.persistence.EntityExistsException;

public interface CustomerService {

    void create (Customer customer) throws EntityExistsException;
    Customer getCustomer(Long customerId) throws EntityExistsException;

}
