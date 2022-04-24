package fw.authservice.repository;

import fw.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameEqualsIgnoreCase(String username);
    Optional<User> findByEmailEqualsIgnoreCase(String email);
}
