package fw.authservice.controller;


import fw.authservice.model.User;
import fw.authservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{userId}")
    public User getInfluencer(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping(path = "dummy/{username}")
    public void registerDummyUser(@PathVariable("username") String username) {
        userService.registerDummyUser(username);
    }

}
