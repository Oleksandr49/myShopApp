package shopApp.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopApp.model.user.User;
import shopApp.model.user.customer.Customer;
import shopApp.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void create(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setIsActive(true);
        customer.setRoles("ROLE_CUSTOMER");
        userRepository.save(customer);
    }


    @Override
    public User read(Long id) {
       return userRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("No Such User");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User update(User newUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseGet(()->{
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }
}
