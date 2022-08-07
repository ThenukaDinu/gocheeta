package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.interfaces.ILocationRespository;
import com.thenuka.gocheetaproject.interfaces.ILocation;
import com.thenuka.gocheetaproject.modals.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService implements ILocation {

    ILocationRespository iLocationRespository;

    @Autowired
    public LocationService(ILocationRespository iLocationRespository) {
        this.iLocationRespository = iLocationRespository;
    }

    public LocationService () {

    }

    @Override
    public List<Location> getLocations() {
        return iLocationRespository.findAll();
    }
}
