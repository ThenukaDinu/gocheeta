package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private IVehicleService vehicleService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicles () {
        try {
            List<VehicleDTO> vehicleDTOS = vehicleService.findAll();
            return new ResponseEntity<>(vehicleDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
