package shopApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUsersByUsername(String name);

}
