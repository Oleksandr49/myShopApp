package shopApp.service.customer.order;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import shopApp.controller.customer.OrderController;
import shopApp.controller.paypal.PayPallController;
import shopApp.controller.products.ProductController;
import shopApp.model.order.CustomerOrder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderWrapper implements RepresentationModelAssembler<CustomerOrder, EntityModel<CustomerOrder>> {

    @Override
    public EntityModel<CustomerOrder> toModel(CustomerOrder entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(OrderController.class).readOrder("", entity.getOrderId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).readOrderHistory("")).withRel("OrderHistory"),
                linkTo(methodOn(PayPallController.class).makePayment(entity)).withRel("Payment"));
    }

    public CollectionModel<EntityModel<CustomerOrder>> toEntityCollection(List<CustomerOrder> orders){
        List<EntityModel<CustomerOrder>> orderHistory = orders.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(orderHistory, linkTo(methodOn(ProductController.class).readAll()).withSelfRel());
    }
}
