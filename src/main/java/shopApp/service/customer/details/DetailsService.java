package shopApp.service.customer.details;

import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Details;

import java.util.Optional;

public interface DetailsService {

    Details readDetails(Long customerId);
    Details updateDetails(Long customerId, Details newDetails);
    Optional<Address> readAddress(Long customerId);
    Address updateAddress(Long customerId, Address newAddress);
}
