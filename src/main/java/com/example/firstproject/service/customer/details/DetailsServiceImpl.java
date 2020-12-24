package com.example.firstproject.service.customer.details;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsServiceImpl implements DetailsService {

    @Autowired
    DetailsRepository detailsRepository;

    @Override
    public void updateDetails(Details newDetails, Long detailsId) {
        Details details = detailsRepository.getOne(detailsId);
        setNewDetails(details, newDetails);
        detailsRepository.save(details);
    }

    @Override
    public void updateAddress(Address newAddress, Long detailsId) {
        Details details = detailsRepository.getOne(detailsId);
        Address address = details.getAddress();
        setNewAddress(address, newAddress);
        detailsRepository.save(details);
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
}
