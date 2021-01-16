package shopApp.model.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shopApp.model.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    private final Boolean isActive;
    @Getter
    private final List<GrantedAuthority> authorities;

    public MyUserDetails(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.getIsActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
