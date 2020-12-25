package com.example.firstproject.service.customer.details;

import com.example.firstproject.controller.customer.CustomerController;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DetailsServiceImpl implements DetailsService {

    @Autowired
    DetailsRepository detailsRepository;

    @Override
    public EntityModel<Details> updateDetails(Details newDetails, Long detailsId) {
        Details details = detailsRepository.getOne(detailsId);
        setNewDetails(details, newDetails);
        detailsRepository.save(details);
        return toModel(details);
    }

    @Override
    public Address updateAddress(Address newAddress, Long detailsId) {
        Details details = detailsRepository.getOne(detailsId);
        if(haveAddress(details)){
            setNewAddress(details.getAddress(), newAddress);
        }
        else {
            details.setAddress(newAddress);
        }
        detailsRepository.save(details);
        return details.getAddress();
    }

    @Override
    public EntityModel<Details> toModel(Details entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).read("")).withSelfRel(),
                linkTo(methodOn(CustomerController.class).readAddress("")).withRel("Address"),
                linkTo(methodOn(CustomerController.class).readOrderHistory("")).withRel("OrderHistory"));
    }


    private void setNewDetails(Details details, Details newDetails){
        details.setEmail(newDetails.getEmail());
        details.setFirstName(newDetails.getFirstName());
        details.setSecondName(newDetails.getSecondName());
        details.setMale(newDetails.getMale());
    }

    private void setNewAddress(Address address, Address newAddress){
        address.setCity(newAddress.getCity());
        address.setCountry(newAddress.getCountry());
        address.setState(newAddress.getState());
        address.setStreet(newAddress.getStreet());
        address.setPostalCode(newAddress.getPostalCode());
    }

    private Boolean haveAddress(Details details){
        return details.getAddress() != null;
    }


}
