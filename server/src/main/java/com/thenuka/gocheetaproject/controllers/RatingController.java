package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.RatingsDTO;
import com.thenuka.gocheetaproject.interfaces.IRatingService;
import com.thenuka.gocheetaproject.modals.Rating;
import com.thenuka.gocheetaproject.requests.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private IRatingService ratingService;

    @PreAuthorize("hasAnyRole('USER', 'DRIVER', 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAll () {
        try {
            List<RatingsDTO> ratingsDTOS = ratingService.findAll();
            return new ResponseEntity<>(ratingsDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'DRIVER', 'ADMIN')")
    @RequestMapping(value = "/{ratingId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOne(@PathVariable(value = "ratingId") int id) {
        try {
            RatingsDTO ratingsDTO = ratingService.findOne(id);
            if (ratingsDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(ratingsDTO, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody RatingRequest ratingRequest) {
        try {
            Rating rating = new Rating();
            RatingsDTO ratingsDTO = ratingService.save(ratingService.convertRequestToEntity(ratingRequest, rating));
            return new ResponseEntity<>(ratingsDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(value = "/{ratingId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "ratingId") int id) {
        try {
            ratingService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(value = "/{ratingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody RatingRequest ratingRequest, @PathVariable(value = "ratingId") int id) {
        try {
            RatingsDTO ratingsDTO = ratingService.update(id, ratingRequest);
            if (ratingsDTO == null) {
                return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(ratingsDTO, HttpStatus.OK);
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
