package eCommerce.myShopApplication.service.customer.details;

import eCommerce.myShopApplication.model.user.customer.Address;
import eCommerce.myShopApplication.model.user.customer.Details;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface DetailsService extends RepresentationModelAssembler<Details, EntityModel<Details>> {

    EntityModel<Details> updateDetails(Details newDetails, Long detailsId);

    Address updateAddress (Address newAddress, Long detailsId);


}
