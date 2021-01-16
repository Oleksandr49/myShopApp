package shopApp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.user.User;
import shopApp.model.user.UserEntityModelAssembler;
import shopApp.service.user.UserService;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserEntityModelAssembler userEntityModelAssembler;

    @GetMapping("/users/{id}")
    public EntityModel<User> readUserById(@PathVariable Long id) {
        final User user = userService.read(id);
        return userEntityModelAssembler.toModel(user);
    }

    @PostMapping("/users")
    public void create(@RequestBody User user) {
        userService.create(user);
    }

    @PutMapping("/users/{id}")
    public EntityModel<User>  update(@RequestBody User user, @PathVariable Long id){
        User newUser = userService.update(user, id);
        return userEntityModelAssembler.toModel(newUser);
    }

    @DeleteMapping("/users/{id}")
    public Boolean delete(@PathVariable Long id) {
        return userService.delete(id);
    }

}
