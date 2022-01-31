package fw.authservice.controller;

import fw.authservice.model.Influencer;
import fw.authservice.model.User;
import fw.authservice.repository.InfluencerRepository;
import fw.authservice.repository.UserRepository;
import fw.authservice.service.InfluencerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;


@RestController
@RequestMapping(path = "api/influencer")
public class InfluencerController {

    private final InfluencerService influencerService;

    @Autowired
    public InfluencerController(InfluencerService influencerService) {
        this.influencerService = influencerService;
    }


    @PostMapping
    public void registerInfluencer(@RequestBody Influencer influencer) {
        System.out.println("in registerInfluencer function");

        LocalDate dummyDate = LocalDate.of(2000,02,04);

//        User dummyUser = new User(
//                null,
//                "testinfluencer",
//                "pass",
//                "ayoub@hotmail.com",
//                32489245740L,
//                "teststreet",
//                dummyDate
//
//        );
//
//        Influencer dummyInfluencer = new Influencer(
//                null,
//                dummyUser,
//                "here influencer"
//        );
//
//
//        influencerService.registerInfluencer(dummyInfluencer);
    }

}
