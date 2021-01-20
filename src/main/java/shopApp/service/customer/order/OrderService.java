package shopApp.service.customer.order;

import shopApp.model.order.CustomerOrder;

import java.util.List;

public interface OrderService {

    CustomerOrder readOrder(Long customerId, Long orderId);

    List<CustomerOrder> getOrderHistory(Long customerId);

    CustomerOrder orderCart(Long customerId);
}
