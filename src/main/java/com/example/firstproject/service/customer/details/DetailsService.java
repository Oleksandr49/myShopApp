package com.example.firstproject.service.customer.details;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Details;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface DetailsService extends RepresentationModelAssembler<Details, EntityModel<Details>> {

    EntityModel<Details> updateDetails(Details newDetails, Long detailsId);

    Address updateAddress (Address address, Long detailsId);


}
