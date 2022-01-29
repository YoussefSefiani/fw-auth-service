package fw.authservice.controller;

import fw.authservice.model.Brand;
import fw.authservice.model.User;
import fw.authservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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

        User dummyUser = new User(
                null,
                "testbrand",
                "sss",
                "youssefbrand@hotmail.com",
                32489245740L,
                "teststreet",
                dummyDate
        );

        Brand dummyBrand = new Brand(
                null,
                dummyUser,
                "here brand"
        );


        brandService.registerBrand(dummyBrand);
    }
}

