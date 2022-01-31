package fw.authservice.service;

import fw.authservice.model.Brand;
import fw.authservice.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final UserService userService;

    @Autowired
    public BrandService(BrandRepository brandRepository, UserService userService) {
        this.brandRepository = brandRepository;
        this.userService = userService;
    }

    public void registerBrand(Brand brand) {

        if(userService.checkUsername(brand.getUser()))
            brandRepository.save(brand);
    }

}
