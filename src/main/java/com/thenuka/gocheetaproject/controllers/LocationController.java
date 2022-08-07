package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.interfaces.ILocation;
import com.thenuka.gocheetaproject.modals.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/location")
@RestController
public class LocationController {
    private ILocation locationService;

    @Autowired
    public LocationController(ILocation locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/getLocations")
    public ResponseEntity sayHello() {
        try {
            List<Location> locations = locationService.getLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
