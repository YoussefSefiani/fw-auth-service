package fw.authservice.controller;

import fw.authservice.model.*;
import fw.authservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/brand")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    @PostMapping
    public void registerBrand(@RequestBody Brand brand) {
        System.out.println("in registerBrand function");

        LocalDate dummyDate = LocalDate.of(2000,02,04);

        List<SocialMedia> dummySocialMedia = List.of(
                new SocialMedia(null, SocialMediaList.SNAPCHAT.name(), "linkbrand"),
                new SocialMedia(null, SocialMediaList.INSTAGRAM.name(), "linkbrand2")
        );

        List<String> dummyLanguages = List.of(
                LanguageList.DUTCH.name(), LanguageList.ENGLISH.name()
        );

        List<String> dummyCountries = List.of(
                CountryList.FRANCE.name(), CountryList.BELGIUM.name()
        );

        List<String> dummySector = List.of(
                SectorList.IT.name(), SectorList.FINANCE.name()
        );

//        User dummyUser = new User(
//                null,
//                "Youssefbrand",
//                "Sefianibrand",
//                "testbrand",
//                "pass",
//                "ayoub@hotmail.com",
//                32489245740L,
//                "teststreetbrand",
//                dummyDate,
//                "profilepicture",
//                5,
//                UserType.BRAND
//
//        );

//        Brand dummyBrand = new Brand(
//                null,
//                dummyUser,
//                dummySocialMedia,
//                "I am a brand",
//                "this is the description brand",
//                dummyLanguages,
//                dummyCountries,
//                dummySector,
//                "here offers brand",
//                "here partnerships brand"
//        );

       // brandService.registerBrand(dummyBrand);
    }
}

