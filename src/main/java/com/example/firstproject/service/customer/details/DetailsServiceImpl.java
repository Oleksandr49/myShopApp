package com.example.firstproject.service.customer.details;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.service.customer.details.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsServiceImpl implements DetailsService {

    @Autowired
    AddressService addressService;

    @Override
    public Details readDetails(Customer customer) {
        return customer.getDetails();
    }

    @Override
    public void updateDetails(Details newDetails, Customer customer) {
        setNewData(customer.getDetails(), newDetails);
    }

    @Override
    public void deleteDetails(Customer customer) {
        removeData(customer.getDetails());
    }

    @Override
    public void createAddress(Address address, Customer customer) {
        addressService.createAddress(address, readDetails(customer));
    }

    @Override
    public Address readAddress(Customer customer) {
        return addressService.readAddress(readDetails(customer));
    }

    @Override
    public void updateAddress(Address newAddress, Customer customer) {
        addressService.updateAddress(newAddress, readDetails(customer));
    }

    @Override
    public void deleteAddress(Customer customer) {
        addressService.deleteAddress(readDetails(customer));
    }

    private void removeData(Details details){
        details.setEmail(null);
        details.setFirstName(null);
        details.setSecondName(null);
        details.setMale(null);
    }

    private void setNewData(Details details, Details newDetails){
        details.setEmail(newDetails.getEmail());
        details.setFirstName(newDetails.getFirstName());
        details.setSecondName(newDetails.getSecondName());
        details.setMale(newDetails.getMale());
    }
}
