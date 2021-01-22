package shopApp.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.user.User;
import shopApp.model.user.UserWrapper;
import shopApp.service.user.UserService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;
    final private UserWrapper userWrapper;

    @GetMapping("/users/{id}")
    public EntityModel<User> readUserById(@PathVariable Long id) {
        return userWrapper.toModel(userService.read(id));
    }

    @PostMapping("/users")
    public void create(@Valid @RequestBody User user) {
        userService.create(user);
    }

    @PutMapping("/users/{id}")
    public EntityModel<User>  update(@Valid @RequestBody User user, @PathVariable Long id){
        return userWrapper.toModel(userService.update(user, id));
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
         userService.delete(id);
    }

}
