package shopApp.service.customer.details;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Details;
import shopApp.repository.DetailsRepository;
import shopApp.service.customer.CustomerService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailsService {

    final private DetailsRepository detailsRepository;
    final private CustomerService customerService;

    @Override
    public Details readDetails(Long customerId) {
        return customerService.getCustomer(customerId).getDetails();
    }

    @Override
    public Details updateDetails(Long customerId, Details newDetails) {
        Long detailsId = readDetails(customerId).getId();
        return detailsRepository.findById(detailsId)
                .map(details -> {
                    details.setEmail(newDetails.getEmail());
                    details.setFirstName(newDetails.getFirstName());
                    details.setSecondName(newDetails.getSecondName());
                    details.setIsMale(newDetails.getIsMale());
                    details.setAddress(newDetails.getAddress());
                    return detailsRepository.save(details);
                }).orElseThrow();
    }

    @Override
    public Optional<Address> readAddress(Long customerId) {
        return Optional.ofNullable(readDetails(customerId).getAddress());
    }

    @Override
    public Address updateAddress(Long customerId, Address newAddress) {
        return readAddress(customerId)
                .map(address -> {
                    address.setCity(newAddress.getCity());
                    address.setCountry(newAddress.getCountry());
                    address.setState(newAddress.getState());
                    address.setStreet(newAddress.getStreet());
                    address.setPostalCode(newAddress.getPostalCode());
                    Details details = readDetails(customerId);
                    details.setAddress(address);
                    detailsRepository.save(details);
                    return address;
                }).orElseThrow();
    }
}
