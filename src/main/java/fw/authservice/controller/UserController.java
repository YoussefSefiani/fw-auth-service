package fw.authservice.controller;


import fw.authservice.model.User;
import fw.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@CrossOrigin(origins="*")
@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(path = "check")
    public boolean checkUserAuthenticated(@RequestHeader("Authorization") String token) {
        return userService.checkUserAuthenticated(token);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public void registerUser(@RequestBody User user) throws Exception {
        userService.registerUser(user);
    }

    @PostMapping(path = "email/{email}")
    public boolean checkEmailAvailable(@PathVariable("email") String email) {
       return userService.checkEmailAvailable(email);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        userService.updateUser(userId, user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping(path = "current")
    public User getConnectedUser() {
       return userService.getConnectedUser();
    }


}
