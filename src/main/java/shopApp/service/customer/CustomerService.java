package shopApp.service.customer;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Customer;

import javax.persistence.EntityExistsException;

public interface CustomerService {

    void create (Customer customer) throws EntityExistsException;
    Customer getCustomer(Long customerId) throws EntityExistsException;

    CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Long id);
    EntityModel<CustomerOrder> readOrder(Long id, Long orderId);
    EntityModel<CustomerOrder> CartToOrder(Long id);
}
