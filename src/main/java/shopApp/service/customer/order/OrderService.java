package shopApp.service.customer.order;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import shopApp.model.order.CustomerOrder;
import shopApp.model.user.customer.Customer;

public interface OrderService extends RepresentationModelAssembler<CustomerOrder, EntityModel<CustomerOrder>> {

    CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Customer customer);
    EntityModel<CustomerOrder> assembleOrder(Customer customer);
    EntityModel<CustomerOrder> readOrder(Customer customer, Long orderId);
}
