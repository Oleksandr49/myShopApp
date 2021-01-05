package shopApp.service.customer.details;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Details;

public interface DetailsService extends RepresentationModelAssembler<Details, EntityModel<Details>> {

    EntityModel<Details> updateDetails(Details newDetails, Long detailsId);

    Address updateAddress (Address newAddress, Long detailsId);


}
