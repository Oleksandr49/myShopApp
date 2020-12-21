package com.example.firstproject.service.customer.details.address;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Details;

public interface AddressService {
    void createAddress(Address address, Details details);
    Address readAddress(Details details);
    void deleteAddress(Details details);
    void updateAddress(Address newAddress, Details details);
}
