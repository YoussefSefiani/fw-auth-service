package fw.authservice.service;

import fw.authservice.model.Influencer;
import fw.authservice.repository.InfluencerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final UserService userService;

    @Autowired
    public InfluencerService(InfluencerRepository influencerRepository, UserService userService) {
        this.influencerRepository = influencerRepository;
        this.userService = userService;
    }

    public void registerInfluencer(Influencer influencer) {

        if(userService.checkUsername(influencer.getUser()))
            influencerRepository.save(influencer);

    }

}
