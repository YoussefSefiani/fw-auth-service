package fw.authservice.service;

import fw.authservice.model.*;
import fw.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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

    public void registerDummyUser(String username) {

        LocalDate dummyDate = LocalDate.of(2000,02,04);

        User dummyUser = new User(
                null,
                "Youssef",
                "Sefiani",
                username,
                passwordEncoder.encode("password"),
                "ayoub@hotmail.com",
                32489245740L,
                "teststreet",
                dummyDate,
                "profilepicture",
                5,
                UserType.INFLUENCER,
                true,
                "USER"
        );



        if (checkUsername(dummyUser)) {
            dummyUser = userRepository.save(dummyUser);
        }

        Long userId = dummyUser.getId();
        RegisterRequest registerRequest = new RegisterRequest(userId, dummyUser.getUserType());

        restTemplate.postForObject("http://fw-profile-service/api/influencer/dummy", registerRequest, RegisterRequest.class);

    }

    public void registerUser(User user) {
//        Optional<Influencer> influencerOptional = influencerRepository.findInfluencerByUsername(influencer.getUser().getUsername());
//
//        if (influencerOptional.isPresent())
//            influencerRepository.save(influencer);
        if (checkUsername(user))
            userRepository.save(user);

    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if (!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }
        userRepository.deleteById(userId);
    }

    //TODO: update user route

    public boolean checkUsername(User user) {
        Optional<User> userOptional = userRepository
                .findByUserNameEqualsIgnoreCase(user.getUserName());
        if(userOptional.isPresent()) {
            throw new IllegalStateException("username already taken");
        }
        return true;
    }
}
