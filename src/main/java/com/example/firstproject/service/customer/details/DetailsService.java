package com.example.firstproject.service.customer.details;

import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Details;

public interface DetailsService {

    void updateDetails(Details newDetails, Long detailsId);

    void updateAddress (Address address, Long detailsId);
}
