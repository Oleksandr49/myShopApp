package com.example.firstproject.service.customer.order;

import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.OrderItem;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.order.OrderState;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.ItemRepository;
import com.example.firstproject.repository.OrderRepository;
import com.example.firstproject.service.customer.details.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    DetailsService detailsService;

    @Override
    public CustomerOrder assembleOrder(Customer customer) {
        CustomerOrder customerOrder = createOrder();
        customerOrder.setTotalCost(customer.getShoppingCart().getTotalCost());
        customerOrder.setDetails(detailsService.readDetails(customer));
        orderRepository.save(customerOrder);
        transferItems(customer.getShoppingCart(), customerOrder);
        return customerOrder;
    }

    private CustomerOrder createOrder(){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreated(LocalDateTime.now());
        customerOrder.setOrderState(OrderState.STATE);
        return customerOrder;
    }

    private void transferItems(ShoppingCart shoppingCart, CustomerOrder customerOrder){
        for(CartItem cartItem : shoppingCart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(customerOrder);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setId(cartItem.getId());
            customerOrder.getOrderItems().add(orderItem);
            itemRepository.save(orderItem);
        }
    }
}
