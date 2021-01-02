package eCommerce.myShopApplication.service.customer.order;

import eCommerce.myShopApplication.controller.customer.CustomerController;
import eCommerce.myShopApplication.controller.paypal.PayPallController;
import eCommerce.myShopApplication.controller.products.ProductController;
import eCommerce.myShopApplication.model.item.CartItem;
import eCommerce.myShopApplication.model.item.OrderItem;
import eCommerce.myShopApplication.model.order.CustomerOrder;
import eCommerce.myShopApplication.model.order.OrderState;
import eCommerce.myShopApplication.model.user.customer.Cart;
import eCommerce.myShopApplication.model.user.customer.Customer;
import eCommerce.myShopApplication.repository.OrderRepository;
import eCommerce.myShopApplication.service.product.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        customerOrder.setTotalCost(customer.getCart().getTotalCost());
        customerOrder.setDetails(customer.getDetails());
        orderRepository.save(customerOrder);
        transferItems(customer.getCart(), customerOrder);
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
                WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).readOrder("", entity.getOrderId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).readOrderHistory("")).withRel("OrderHistory"),
                WebMvcLinkBuilder.linkTo(methodOn(PayPallController.class).makePayment(entity)).withRel("Payment"));
    }



    private CollectionModel<EntityModel<CustomerOrder>> toEntityCollection(List <CustomerOrder> orders){
        List<EntityModel<CustomerOrder>> orderHistory = orders.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(orderHistory, WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).readAll()).withSelfRel());
    }

    private CustomerOrder createOrder(){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreated(LocalDateTime.now());
        customerOrder.setOrderState(OrderState.STATE);
        return customerOrder;
    }

    private CustomerOrder findOrder(List<CustomerOrder> orderHistory, Long orderId){
        for(CustomerOrder order : orderHistory){
            if(order.getOrderId().equals(orderId)){
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
