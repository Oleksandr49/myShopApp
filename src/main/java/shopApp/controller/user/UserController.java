package shopApp.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.user.User;
import shopApp.model.user.UserWrapper;
import shopApp.service.user.UserService;


@RestController
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    final private UserWrapper userWrapper;

    @GetMapping("/users/{id}")
    public EntityModel<User> readUserById(@PathVariable Long id) {
        final User user = userService.read(id);
        return userWrapper.toModel(user);
    }

    @PostMapping("/users")
    public void create(@RequestBody User user) {
        userService.create(user);
    }

    @PutMapping("/users/{id}")
    public EntityModel<User>  update(@RequestBody User user, @PathVariable Long id){
        User newUser = userService.update(user, id);
        return userWrapper.toModel(newUser);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
         userService.delete(id);
    }

}
