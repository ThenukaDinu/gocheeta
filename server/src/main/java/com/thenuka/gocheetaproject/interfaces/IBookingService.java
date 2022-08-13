package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.modals.Booking;

public interface IBookingService {
    BookingDTO convertEntityToDto(Booking booking);

    boolean existsById(int id);

    Booking findById(int id);
}
