package shopApp.model.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shopApp.model.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used by Spring security as implementation of UserDetails
 * for user authentication. Works with User entity.
 * @see UserDetails
 * @see User
 */
public class MyUserDetails implements UserDetails {
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    private final Boolean isActive;
    @Getter
    private final List<GrantedAuthority> authorities;

    /**
     * @param user User entity corresponding to required UserDetails
     */
    public MyUserDetails(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.getIsActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }

    /**
     *
     * @return true always
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *
     * @return true always
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *
     * @return true always
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     * @return true when user is active.
     */
    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
