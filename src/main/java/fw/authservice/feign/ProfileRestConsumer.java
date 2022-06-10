package fw.authservice.feign;

import fw.authservice.model.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="fw-profile-service", url="${app.services.profile.url}")
public interface ProfileRestConsumer {

    @PostMapping("/api/influencer")
    void registerInfluencer(@RequestBody RegisterRequest registerRequest);

    @PostMapping("/api/brand")
    void registerBrand(@RequestBody RegisterRequest registerRequest);

}
