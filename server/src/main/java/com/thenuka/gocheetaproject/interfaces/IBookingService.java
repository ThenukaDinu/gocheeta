package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.requests.BookingRequest;

import java.util.List;

public interface IBookingService {
    BookingDTO convertEntityToDto(Booking booking);

    boolean existsById(int id);

    Booking findById(int id);

    BookingDTO findByIdDto(int id);

    List<BookingDTO> findAll();

    void deleteById(int id);

    BookingDTO save(Booking booking);

    Booking convertRequestToEntity(BookingRequest bookingRequest, Booking booking);

    BookingDTO update(int id, BookingRequest bookingRequest);
}
