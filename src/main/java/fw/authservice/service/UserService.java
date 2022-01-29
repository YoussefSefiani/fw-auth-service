package fw.authservice.service;


import fw.authservice.model.User;
import fw.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUsername(User user) {
        Optional<User> userOptional = userRepository
                .findByUsernameEqualsIgnoreCase(user.getUsername());
        if(userOptional.isPresent()) {
            throw new IllegalStateException("name already taken");
        }
        return true;
    }

}
