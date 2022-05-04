package fw.authservice.service;

import fw.authservice.feign.ProfileRestConsumer;
import fw.authservice.model.*;
import fw.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    //private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRestConsumer consumer;

    @Autowired
    public UserService(/*RestTemplate restTemplate,*/ UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRestConsumer consumer) {
        //this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.consumer = consumer;
    }

    public Long getConnectedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getName());
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id %s does not exist", userId))
                );

    }

    public User getConnectedUser() {
        return userRepository.findById(getConnectedUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id %s does not exist", getConnectedUserId()))
                );
    }

    public Long getUserIdByUserName(String userName) {

        User user = userRepository.findByUserNameEqualsIgnoreCase(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with username %s does not exist", userName)
                ));
        return user.getId();

    }

    public boolean checkUserAuthenticated(String token) {
        return true;
    }

    public boolean checkEmailAvailable(String email) {
        Optional<User> userOptional = userRepository.findByEmailEqualsIgnoreCase(email);
        System.out.println("userOptional is: " + userOptional.isEmpty());
        return userOptional.isEmpty();
    }

    public void registerUser(User user) {


        Optional<User> userOptional = userRepository.findByUserNameEqualsIgnoreCase(user.getUserName());
        System.out.println(userOptional);

        if (userOptional.isEmpty()) {
            System.out.println(user.getBirthdate());
            Date newBirthDate = new Date(user.getBirthdate().getYear(), user.getBirthdate().getMonth(), user.getBirthdate().getDay());
            user.setBirthdate(newBirthDate);
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            user.setActive(true);
            user.setRoles("USER");
            //TODO: change rating implementation (maybe in profile service)
            user.setRating(0);
            userRepository.save(user);
            Long userId = user.getId();
            RegisterRequest registerRequest = new RegisterRequest(userId, user.getUserType());
           // restTemplate.postForObject("http://fw-profile-service/api/influencer", registerRequest, RegisterRequest.class);
            consumer.registerInfluencer(registerRequest);
            System.out.println("AFTER RESTTEMPLATE " + user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User with username %s already exists", userOptional.get().getUserName()));
        }
    }

    @Transactional
    public void updateUser(Long userId, User newUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %s does not exist", userId))
                );

        String username = newUser.getUserName() != null ? newUser.getUserName() : null;
        String password = newUser.getPassword() != null ? newUser.getPassword() : null;

        if (username != null && username.length() > 0 && !Objects.equals(user.getUserName(), username)) {
            Optional<User> userOptional = userRepository.findByUserNameEqualsIgnoreCase(username);
            if (userOptional.isEmpty()) {
                user.setUserName(username);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("User with username %s already exists", username));
            }
        } else if (Objects.equals(user.getUserName(), username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot choose the same username");
        }
        ;

        if (password != null && password.length() > 0 && !passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
        } else if (password != null && passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot choose the same password");
        }
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User with id %s does not exist", userId));
        }
        userRepository.deleteById(userId);
    }


}
