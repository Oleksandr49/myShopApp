package shopApp.service.customer.details;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import shopApp.controller.customer.CustomerController;
import shopApp.controller.customer.DetailsController;
import shopApp.model.user.customer.Details;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DetailsWrapper implements RepresentationModelAssembler<Details, EntityModel<Details>> {

    @Override
    public EntityModel<Details> toModel(Details entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(DetailsController.class).readDetails("")).withSelfRel(),
                linkTo(methodOn(DetailsController.class).readAddress("")).withRel("Address"),
                linkTo(methodOn(CustomerController.class).readOrderHistory("")).withRel("OrderHistory"));
    }
}
