package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.repositories.ILocationRespository;
import com.thenuka.gocheetaproject.interfaces.ILocationService;
import com.thenuka.gocheetaproject.modals.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService implements ILocationService {

    ILocationRespository locationRespository;

    @Autowired
    public LocationService(ILocationRespository locationRespository) {
        this.locationRespository = locationRespository;
    }

    public LocationService () {

    }

    @Override
    public List<Location> getLocations() {
        return locationRespository.findAll();
    }
}
