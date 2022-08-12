package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.interfaces.IBookingService;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.repositories.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public BookingDTO convertEntityToDto(Booking book) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(book.getId());
        bookingDTO.setPrice(book.getPrice());
        bookingDTO.setStatus(book.getStatus());
        bookingDTO.setTripPlacedTime(book.getTripPlacedTime());
        bookingDTO.setTripCanceledTime(book.getTripCanceledTime());
        bookingDTO.setTripScheduledTime(book.getTripScheduledTime());
        bookingDTO.setTripFinishedTime(book.getTripFinishedTime());
        bookingDTO.setTripStartTime(book.getTripStartTime());
        bookingDTO.setPickUpLocation(book.getPickUpLocation());
        bookingDTO.setDropOffLocation(book.getDropOffLocation());
        if (book.getVehicle() != null) bookingDTO.setVehicleId(book.getVehicle().getId());
        if (book.getDriver() != null)  bookingDTO.setDriverId(book.getDriver().getId());
        if (book.getUser() != null)  bookingDTO.setUserId(book.getUser().getId());
        return bookingDTO;
    }
}
