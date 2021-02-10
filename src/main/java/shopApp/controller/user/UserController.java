package shopApp.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.user.User;
import shopApp.model.user.UserWrapper;
import shopApp.service.jwt.JwtService;
import shopApp.service.user.UserService;

import javax.validation.Valid;

//Description
@RestController
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;
    final private UserWrapper userWrapper;
    final private JwtService jwtService;
    private final String headerName = "Authorization";

    @GetMapping("/users/{id}")
    public EntityModel<User> readUserById(@PathVariable Long id) {
        return userWrapper.toModel(userService.read(id));
    }

    @PostMapping("/users")
    public void create(@Valid @RequestBody User user) {
        userService.create(user);
    }

    @DeleteMapping("/users")
    public void delete(@RequestHeader(name = headerName)String header) {
         userService.delete(jwtService.getCustomerIdFromAuthHeader(header));
    }

}
