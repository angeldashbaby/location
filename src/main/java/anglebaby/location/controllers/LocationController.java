package anglebaby.location.controllers;

import anglebaby.location.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService service;

    @PostMapping
    public String findLocation(@RequestBody String payload) {
        try {
//            System.out.println("test");
            return service.findLocation(payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
