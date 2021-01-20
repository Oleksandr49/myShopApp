package shopApp.service.customer.details;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import shopApp.controller.customer.DetailsController;
import shopApp.controller.customer.OrderController;
import shopApp.model.user.customer.Details;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetailsWrapper implements RepresentationModelAssembler<Details, EntityModel<Details>> {

    @Override
    public EntityModel<Details> toModel(Details entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(DetailsController.class).readDetails("")).withSelfRel(),
                linkTo(methodOn(DetailsController.class).readAddress("")).withRel("Address"),
                linkTo(methodOn(OrderController.class).readOrderHistory("")).withRel("OrderHistory"));
    }
}
