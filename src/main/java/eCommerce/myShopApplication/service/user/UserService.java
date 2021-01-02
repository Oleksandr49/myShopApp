package eCommerce.myShopApplication.service.user;

import eCommerce.myShopApplication.model.user.User;
import eCommerce.myShopApplication.model.user.customer.Customer;

public interface UserService {

     void create (User user);
     void create (Customer customer);

     User read (Long id);
     Boolean delete (Long id);
     User update (User user, Long id);
}
