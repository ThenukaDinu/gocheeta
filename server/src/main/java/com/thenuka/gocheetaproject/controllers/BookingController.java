package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.dto.RoleDTO;
import com.thenuka.gocheetaproject.interfaces.IBookingService;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.requests.BookingRequest;
import com.thenuka.gocheetaproject.requests.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private IBookingService bookingService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAll () {
        try {
            List<BookingDTO> bookingDTOS = bookingService.findAll();
            return new ResponseEntity<>(bookingDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(value = "/{bookingId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOne(@PathVariable(value = "bookingId") int id) {
        try {
            BookingDTO bookingDTO = bookingService.findByIdDto(id);
            if (bookingDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BookingRequest bookingRequest) {
        try {
            Booking booking = new Booking();
            bookingRequest.setTripPlacedTime(LocalDateTime.now());
            BookingDTO bookingDTO = bookingService.save(bookingService.convertRequestToEntity(bookingRequest, booking));
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(value = "/{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "bookingId") int id) {
        try {
            bookingService.deleteById(id);
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
    @RequestMapping(value = "/{bookingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody BookingRequest bookingRequest, @PathVariable(value = "bookingId") int id) {
        try {
            if (bookingRequest.getStatus() == "TripStarted") {
                bookingRequest.setTripStartTime(LocalDateTime.now());
            }
            if (bookingRequest.getStatus() == "TripEnded") {
                bookingRequest.setTripFinishedTime(LocalDateTime.now());
            }
            if (bookingRequest.getStatus() == "Canceled") {
                bookingRequest.setTripCanceledTime(LocalDateTime.now());
            }
            BookingDTO bookingDTO = bookingService.update(id, bookingRequest);
            if (bookingDTO == null) {
                return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
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
