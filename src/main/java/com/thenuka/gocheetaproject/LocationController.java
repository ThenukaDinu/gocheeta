package com.thenuka.gocheetaproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/location")
@RestController
public class LocationController {
    @GetMapping("/getLocations")
    public String sayHello() {
        return "This is get locations";
    }
}
