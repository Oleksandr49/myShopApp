package eCommerce.myShopApplication.controller.user;

import eCommerce.myShopApplication.model.user.User;
import eCommerce.myShopApplication.model.user.UserEntityModelAssembler;
import eCommerce.myShopApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



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
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        userService.create(user);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<User>  update(@RequestBody User user, @PathVariable Long id){
        User newUser = userService.update(user, id);
        return userEntityModelAssembler.toModel(newUser);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean delete(@PathVariable Long id) {

        return userService.delete(id);
    }

}
