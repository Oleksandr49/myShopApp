package shopApp.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopApp.model.user.customer.Customer;
import shopApp.service.customer.CustomerService;

import javax.persistence.PersistenceException;
import javax.validation.Valid;


//Description
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    final private CustomerService customerService;

    @PostMapping
    public void create(@Valid @RequestBody Customer customer) throws PersistenceException {
        customerService.create(customer);
    }
}
