package eCommerce.myShopApplication.repository;

import eCommerce.myShopApplication.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUsersByUsername(String name);

}
