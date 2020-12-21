package com.example.firstproject.service.customer.details;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;

public interface DetailsService {
    Details readDetails(Customer customer);
    void updateDetails(Details newDetails, Customer customer);
    void deleteDetails(Customer customer);

    void createAddress(Address address, Customer customer);
    Address readAddress (Customer customer);
    void updateAddress (Address address, Customer customer);
    void deleteAddress (Customer customer);

}
