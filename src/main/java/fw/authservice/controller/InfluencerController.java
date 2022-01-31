package fw.authservice.controller;

import fw.authservice.model.*;
import fw.authservice.service.InfluencerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


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
        List<SocialMedia> dummySocialMedia = List.of(
                new SocialMedia(null, SocialMediaList.SNAPCHAT.name(), "testlink"),
                new SocialMedia(null, SocialMediaList.INSTAGRAM.name(), "testlink2")
                );

        List<Language> dummyLanguages =  List.of(
                new Language(null, LanguageList.DUTCH.name()),
                new Language(null, LanguageList.ENGLISH.name())
        );

        List<Country> dummyCountries = List.of(
                new Country(null, CountryList.BELGIUM.name()),
                new Country(null, CountryList.FRANCE.name())
        );


        List<Sector> dummySector = List.of(
                new Sector(null, SectorList.IT.name()),
                new Sector(null, SectorList.FINANCE.name())
       );

        User dummyUser = new User(
                null,
                "Youssef",
                "Sefiani",
                "testinfluencer",
                "pass",
                "ayoub@hotmail.com",
                32489245740L,
                "teststreet",
                dummyDate,
                "profilepicture",
                5,
                UserType.INFLUENCER

        );

        Influencer dummyInfluencer = new Influencer(
                null,
                dummyUser,
                "BE123456742546",
                dummySocialMedia,
                "I am an influencer",
                "this is the description",
                dummyLanguages,
                dummyCountries,
                dummySector,
                "here offers",
                "here partnerships"
        );


        influencerService.registerInfluencer(dummyInfluencer);
    }

}
