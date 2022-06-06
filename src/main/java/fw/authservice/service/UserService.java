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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRestConsumer consumer;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRestConsumer consumer) {
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

    public boolean checkAvailable(CredentialsAvailable credentials) {
        Optional<User> userOptionalEmail = userRepository.findByEmailEqualsIgnoreCase(credentials.getEmail());
        Optional<User> userOptionalUserName = userRepository.findByUserNameEqualsIgnoreCase(credentials.getUserName());
        if(userOptionalEmail.isEmpty() && userOptionalUserName.isEmpty()) {
            return true;
        } else if(userOptionalEmail.isPresent() && userOptionalUserName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email");
        } else if(userOptionalUserName.isPresent() && userOptionalEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username");
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "both");
        }
    }



    public void registerUser(User user) {
            Date newBirthDate = new Date(user.getBirthdate().getYear(), user.getBirthdate().getMonth(), user.getBirthdate().getDay());
            user.setBirthdate(newBirthDate);

            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            user.setActive(true);
            user.setRoles("USER");
            user.setRating(0);
            userRepository.save(user);

            Long userId = user.getId();
            UserType userType = user.getUserType();

            if(userType.equals(UserType.INFLUENCER)) {
                RegisterRequest registerRequest = new RegisterRequest(userId, userType);
                consumer.registerInfluencer(registerRequest);
                return;
            }
            RegisterRequest registerRequest = new RegisterRequest(userId, userType);
            consumer.registerBrand(registerRequest);
    }

    @Transactional
    public void updateUser(Long userId, User newUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %s does not exist", userId))
                );

        String username = newUser.getUserName() != null ? newUser.getUserName() : null;
        boolean isAvailable = false;

        if (username != null && username.length() > 0 && !Objects.equals(user.getUserName(), username)) {
            Optional<User> userOptional = userRepository.findByUserNameEqualsIgnoreCase(username);
            if (userOptional.isEmpty()) {
                isAvailable = true;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("User with username %s already exists", username));
            }
        } else if (Objects.equals(user.getUserName(), username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot choose the same username");
        }

        if(isAvailable) {
            user.updateUser(newUser);
        }

        userRepository.save(user);
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

    public HashMap<Long, String> getAllPartnershipBrandNames(List<Long> brandIds) {
        System.out.println(brandIds);
        List<User> brands = userRepository.findByIdIn(brandIds);
        HashMap<Long, String> brandMap = new HashMap<>();
        System.out.println(brands);

        brands.forEach(brand -> {
            brandMap.putIfAbsent(brand.getId(), brand.getUserName());
        });

        return brandMap;
    }
}
