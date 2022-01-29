package fw.authservice.repository;

import fw.authservice.model.Influencer;
import fw.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
   // Optional<Influencer> findInfluencerByUsername(String username);
}
