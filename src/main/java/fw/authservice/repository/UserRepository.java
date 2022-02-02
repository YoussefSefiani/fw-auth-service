package fw.authservice.repository;

import fw.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // @Query("select u from User u where upper(u.username) = upper(?1)")
    Optional<User> findByUserNameEqualsIgnoreCase(String username);
}
