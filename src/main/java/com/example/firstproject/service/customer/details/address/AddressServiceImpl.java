package com.example.firstproject.service.customer.details.address;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Details;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    @Override
    public void createAddress(Address address, Details details) {
        details.setAddress(address);
    }

    @Override
    public Address readAddress(Details details) {
        return details.getAddress();
    }

    @Override
    public void deleteAddress(Details details) {
        details.setAddress(null);
    }

    @Override
    public void updateAddress(Address newAddress, Details details) {
        setNewData(details.getAddress(), newAddress);
    }

    private void setNewData(Address address, Address newAddress){
        address.setCity(newAddress.getCity());
        address.setCountry(newAddress.getCountry());
        address.setPostalCode(newAddress.getPostalCode());
        address.setState(newAddress.getState());
        address.setStreet(newAddress.getStreet());
    }
}
