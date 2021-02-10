package shopApp.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Details;
import shopApp.service.customer.details.DetailsService;
import shopApp.service.customer.details.DetailsWrapper;
import shopApp.service.jwt.JwtService;

import javax.validation.Valid;


//Description
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers/details")
public class DetailsController {

    private final String headerName = "Authorization";

    final private DetailsService detailsService;
    final private JwtService jwtService;
    final private DetailsWrapper detailsWrapper;

    @GetMapping
    public EntityModel<Details> readDetails(@RequestHeader(name = headerName)String header) {
        return detailsWrapper.toModel(detailsService.readDetails(jwtService.getCustomerIdFromAuthHeader(header)));
    }

    @PutMapping
    public EntityModel<Details>updateDetails(@Valid @RequestBody Details details, @RequestHeader(name = headerName)String header){
        return detailsWrapper.toModel(detailsService.updateDetails(jwtService.getCustomerIdFromAuthHeader(header), details));
    }

    @GetMapping("/addresses")
    public Address readAddress(@RequestHeader(name = headerName)String header) {
        return detailsService.readAddress(jwtService.getCustomerIdFromAuthHeader(header)).orElseThrow();
    }

    @PutMapping("/addresses")
    public Address updateAddress(@Valid @RequestBody Address address, @RequestHeader(name = headerName)String header){
        return detailsService.updateAddress(jwtService.getCustomerIdFromAuthHeader(header), address);
    }
}
