package shopApp.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shopApp.model.jwt.MyUserDetails;
import shopApp.model.user.User;
import shopApp.repository.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

    final private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUsersByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("Username " + s + "not found");
        }
        else {
            return new MyUserDetails(user);
        }
    }
}
