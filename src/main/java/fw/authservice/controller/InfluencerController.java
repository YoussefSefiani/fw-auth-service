package fw.authservice.controller;

import fw.authservice.model.*;
import fw.authservice.service.InfluencerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping
    public List<Influencer> getInfluencers() {
        return influencerService.getInfluencers();
    }

    @GetMapping(path = "{influencerId}")
    public Influencer getInfluencer(@PathVariable("influencerId") Long influencerId) {
        return influencerService.getInfluencer(influencerId);
    }

    @PostMapping(path = "dummy")
    public void registerDummyInfluencer() {
        influencerService.registerDummyInfluencer();
    }

    @PostMapping
    public void registerInfluencer(@RequestBody Influencer influencer) {
        System.out.println("in registerInfluencer function");
        influencerService.registerInfluencer(influencer);
    }

    @DeleteMapping(path = "{influencerId}")
    public void deleteInfluencer(@PathVariable("influencerId") Long influencerId) {
        influencerService.deleteInfluencer(influencerId);
    }

    @PutMapping(path = "{influencerId}")
    public void updateInfluencer(@PathVariable("influencerId") Long influencerId, @RequestBody Influencer influencer) {
        influencerService.updateInfluencer(influencerId, influencer);
    }

}
