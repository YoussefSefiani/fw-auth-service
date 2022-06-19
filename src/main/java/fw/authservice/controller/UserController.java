package fw.authservice.controller;


import fw.authservice.model.BrandIdWrapper;
import fw.authservice.model.CredentialsAvailable;
import fw.authservice.model.InfluencerIdWrapper;
import fw.authservice.model.User;
import fw.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "ping")
    public String ping() {
        return "pong";
    }

    @PostMapping(path = "brandnames")
    public HashMap<Long, String> getAllPartnershipBrandNames(@RequestBody BrandIdWrapper brandIds) {
        return userService.getAllPartnershipBrandNames(brandIds.getBrandIds());
    }

    @PostMapping(path = "influencernames")
    public HashMap<Long, String> getAllPartnershipInfluencerNames(@RequestBody InfluencerIdWrapper influencerIds) {
        return userService.getAllPartnershipInfluencerNames(influencerIds.getInfluencerIds());
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

    @PostMapping(path = "available")
    public boolean checkAvailable(@RequestBody CredentialsAvailable credentials) {
       return userService.checkAvailable(credentials);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody User newUser) {
        userService.updateUser(userId, newUser);
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
