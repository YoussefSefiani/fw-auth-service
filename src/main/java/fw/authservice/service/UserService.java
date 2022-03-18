package fw.authservice.service;

import fw.authservice.model.*;
import fw.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RestTemplate restTemplate, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));

    }

    public User getConnectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUserNameEqualsIgnoreCase(currentPrincipalName)
                .orElseThrow(() -> new IllegalStateException(
                        "User with username " + currentPrincipalName + " does not exist"
                ));
    }

    public boolean checkEmailAvailable(String email) {
        Optional<User> userOptional = userRepository.findByEmailEqualsIgnoreCase(email);
        System.out.println("userOptional is: " + userOptional.isEmpty());
        return userOptional.isEmpty();
    }

    public void registerUser(User user) {

        System.out.println(user);
        Optional<User> userOptional = userRepository.findByUserNameEqualsIgnoreCase(user.getUserName());
        System.out.println(userOptional);

        if (userOptional.isEmpty()) {
            System.out.println(user.getBirthdate());
            Date newBirthDate = new Date(user.getBirthdate().getYear(), user.getBirthdate().getMonth(), user.getBirthdate().getDay());
            user.setBirthdate(newBirthDate);
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            Long userId = user.getId();
            RegisterRequest registerRequest = new RegisterRequest(userId, user.getUserType());
            restTemplate.postForObject("http://fw-profile-service/api/influencer", registerRequest, RegisterRequest.class);
        } else {
            throw new IllegalStateException(String.format("User with username %s already exists", userOptional.get().getUserName()));
        }
    }

    @Transactional
    public void updateUser(Long userId, User newUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "user with id" + userId + "does not exist"
                ));

        String username = newUser.getUserName() != null ? newUser.getUserName() : null;
        String password = newUser.getPassword() != null ? newUser.getPassword() : null;

        if (username != null && username.length() > 0 && !Objects.equals(user.getUserName(), username)) {
            Optional<User> userOptional = userRepository.findByUserNameEqualsIgnoreCase(username);
            if (userOptional.isEmpty()) {
                user.setUserName(username);
            } else {
                throw new IllegalStateException(String.format("User with username %s already exists, please choose another username", username));
            }
        } else if (Objects.equals(user.getUserName(), username)) {
            throw new IllegalStateException("you cannot choose the same username");
        }
        ;

        if (password != null && password.length() > 0 && !passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
        } else if (password != null && passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("you cannot choose the same password");
        }
    }

    public boolean checkUsername(User user) {
        Optional<User> userOptional = userRepository
                .findByUserNameEqualsIgnoreCase(user.getUserName());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("username already taken");
        }
        return true;
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if (!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }
        userRepository.deleteById(userId);
    }

}
