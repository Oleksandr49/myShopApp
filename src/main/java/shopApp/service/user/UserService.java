package shopApp.service.user;

import shopApp.model.user.User;
import shopApp.model.user.customer.Customer;

public interface UserService {

     void create (User user);
     void create (Customer customer);

     User read (Long id);
     void delete (Long id);
     User update (User user, Long id);
}
