package eCommerce.myShopApplication.controller.user;

import eCommerce.myShopApplication.model.user.User;
import eCommerce.myShopApplication.model.user.UserEntityModelAssembler;
import eCommerce.myShopApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserEntityModelAssembler userEntityModelAssembler;

    @GetMapping(value = "/users/{id}")
    public EntityModel<User> readUserById(@PathVariable Long id) {
        final User user = userService.read(id);

        return userEntityModelAssembler.toModel(user);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    public EntityModel<User>  update(@RequestBody User user, @PathVariable Long id){
        User newUser = userService.update(user, id);
        return userEntityModelAssembler.toModel(newUser);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return userService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
