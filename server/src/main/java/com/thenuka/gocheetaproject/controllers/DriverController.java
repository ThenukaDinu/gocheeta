package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.DriverDTO;
import com.thenuka.gocheetaproject.interfaces.IDriverService;
import com.thenuka.gocheetaproject.modals.Driver;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.requests.DriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private IDriverService driverService;

    @PreAuthorize("hasAnyRole('USER', 'DRIVER', 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAll () {
        try {
            List<DriverDTO> driverDTOS = driverService.findAll();
            return new ResponseEntity<>(driverDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'DRIVER', 'ADMIN')")
    @RequestMapping(value = "/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOne(@PathVariable(value = "driverId") int id) {
        try {
            DriverDTO driverDTO = driverService.findOne(id);
            if (driverDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(driverDTO, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody DriverRequest driverRequest) {
        try {
            Driver driver = new Driver();
            driver.setBookings(Collections.emptySet());
            driver.setVehicles(Collections.emptySet());
            driver.setRatings(Collections.emptySet());
            User user = new User();
            user.setBookings(Collections.emptySet());
            user.setRatings(Collections.emptySet());

            DriverDTO driverDTO = driverService.save(driverService.convertRequestToEntity(driverRequest, user, driver));
            return new ResponseEntity<>(driverDTO, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Username and NIC should be unique", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{driverId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "driverId") int id) {
        try {
            driverService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{driverId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody DriverRequest driverRequest, @PathVariable(value = "driverId") int id) {
        try {
            DriverDTO driverDTO = driverService.update(id, driverRequest);
            if (driverDTO == null) {
                return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(driverDTO, HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
