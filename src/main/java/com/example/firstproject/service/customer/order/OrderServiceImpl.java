package com.example.firstproject.service.customer.order;

import com.example.firstproject.controller.customer.CustomerController;
import com.example.firstproject.controller.paypal.PayPallController;
import com.example.firstproject.controller.products.ProductController;
import com.example.firstproject.model.item.CartItem;
import com.example.firstproject.model.item.OrderItem;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.order.OrderState;
import com.example.firstproject.model.user.customer.Cart;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.repository.OrderRepository;
import com.example.firstproject.service.product.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;

    @Override
    public CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Customer customer) {
        return toEntityCollection(customer.getDetails().getOrderHistory());
    }

    @Override
    public EntityModel<CustomerOrder> assembleOrder(Customer customer) {
        CustomerOrder customerOrder = createOrder();
        customerOrder.setTotalCost(customer.getShoppingCart().getTotalCost());
        customerOrder.setDetails(customer.getDetails());
        orderRepository.save(customerOrder);
        transferItems(customer.getShoppingCart(), customerOrder);
        return toModel(customerOrder);
    }

    @Override
    public EntityModel<CustomerOrder> readOrder(Customer customer, Long orderId) {
            List<CustomerOrder> orderHistory = customer.getDetails().getOrderHistory();
            return toModel(findOrder(orderHistory, orderId));
    }

    @Override
    public EntityModel<CustomerOrder> toModel(CustomerOrder entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).readOrder("", entity.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).readOrderHistory("")).withRel("OrderHistory"),
                linkTo(methodOn(PayPallController.class).makePayment(entity)).withRel("Payment"));
    }



    private CollectionModel<EntityModel<CustomerOrder>> toEntityCollection(List <CustomerOrder> orders){
        List<EntityModel<CustomerOrder>> orderHistory = orders.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(orderHistory, linkTo(methodOn(ProductController.class).readAll()).withSelfRel());
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

    private void transferItems(Cart cart, CustomerOrder customerOrder){
        for(CartItem cartItem : cart.getCartItems()){
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
