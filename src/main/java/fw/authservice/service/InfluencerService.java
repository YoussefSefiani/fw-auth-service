package fw.authservice.service;

import fw.authservice.model.*;
import fw.authservice.repository.InfluencerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InfluencerService(InfluencerRepository influencerRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.influencerRepository = influencerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Influencer> getInfluencers() {
        return influencerRepository.findAll();
    }

    public Influencer getInfluencer(Long influencerId) {

        return influencerRepository.findById(influencerId)
                .orElseThrow(() -> new IllegalStateException(
                        "influencer with id " + influencerId + " does not exist"
                ));

    }

    public void registerDummyInfluencer() {

        System.out.println("in registerDummyInfluencer function");

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

        if (userService.checkUsername(dummyInfluencer.getUser()))
            influencerRepository.save(dummyInfluencer);

    }

    public void registerInfluencer(Influencer influencer) {

//        Optional<Influencer> influencerOptional = influencerRepository.findInfluencerByUsername(influencer.getUser().getUsername());
//
//        if (influencerOptional.isPresent())
//            influencerRepository.save(influencer);

        if (userService.checkUsername(influencer.getUser()))
            influencerRepository.save(influencer);

    }

    public void deleteInfluencer(Long influencerId) {
        boolean exists = influencerRepository.existsById(influencerId);

        if (!exists) {
            throw new IllegalStateException("influencer with id " + influencerId + " does not exists");
        }
        influencerRepository.deleteById(influencerId);
    }

    @Transactional
    public void updateInfluencer(Long influencerId, Influencer newInfluencer) {
        Influencer influencer = influencerRepository.findById(influencerId)
                .orElseThrow(() -> new IllegalStateException(
                        "influencer with id " + influencerId + "does not exist"
                ));

        //TODO: add more elements to update
        String description = newInfluencer.getDescription();
        String headTitle = newInfluencer.getHeadTitle();
        String ibanNumber = newInfluencer.getIbanNumber();
        String offers = newInfluencer.getOffers();
        String partnerships = newInfluencer.getPartnerships();

        if (description != null && description.length() > 0 && !Objects.equals(influencer.getDescription(), description))
            influencer.setDescription(description);

        if (headTitle != null && headTitle.length() > 0 && !Objects.equals(influencer.getHeadTitle(), headTitle))
            influencer.setDescription(description);

        if (ibanNumber != null && ibanNumber.length() > 0 && !Objects.equals(influencer.getIbanNumber(), ibanNumber))
            influencer.setDescription(description);

        if (offers != null && offers.length() > 0 && !Objects.equals(influencer.getOffers(), offers))
            influencer.setDescription(description);

        if (partnerships != null && partnerships.length() > 0 && !Objects.equals(influencer.getPartnerships(), partnerships))
            influencer.setDescription(description);

        //TODO: check if not necessary to add repository

    }

}
