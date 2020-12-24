package com.example.firstproject.service.customer.order;

import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.OrderItem;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.order.OrderState;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.OrderRepository;
import com.example.firstproject.service.product.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;

    @Override
    public CustomerOrder assembleOrder(Customer customer) {
        CustomerOrder customerOrder = createOrder();
        customerOrder.setTotalCost(customer.getShoppingCart().getTotalCost());
        customerOrder.setDetails(customer.getDetails());
        orderRepository.save(customerOrder);
        transferItems(customer.getShoppingCart(), customerOrder);
        return customerOrder;
    }

    @Override
    public CustomerOrder readOrder(Customer customer, Long orderId) {
            List<CustomerOrder> orderHistory = customer.getDetails().getOrderHistory();
            return findOrder(orderHistory, orderId);
    }

    private CustomerOrder createOrder(){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreated(LocalDateTime.now());
        customerOrder.setOrderState(OrderState.STATE);
        return customerOrder;
    }

    private CustomerOrder findOrder(List<CustomerOrder> orderHistory, Long orderId){
        for(CustomerOrder order : orderHistory){
            if(order.getId().equals(orderId)){
                return order;
            }
        }
        return null;
    }

    private void transferItems(ShoppingCart shoppingCart, CustomerOrder customerOrder){
        for(CartItem cartItem : shoppingCart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(customerOrder);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setId(cartItem.getId());
            orderItem.setCost(cartItem.getCost());
            customerOrder.getOrderItems().add(orderItem);
            itemService.saveItem(orderItem);
        }
    }
}
