package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.enums.publicEnum;
import com.thenuka.gocheetaproject.interfaces.IBookingService;
import com.thenuka.gocheetaproject.interfaces.IDriverService;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.repositories.IBookingRepository;
import com.thenuka.gocheetaproject.requests.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDriverService driverService;
    @Autowired
    private IVehicleService vehicleService;

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
        if (book.getRating() != null) bookingDTO.setRatingId(book.getRating().getId());
        return bookingDTO;
    }

    @Override
    public boolean existsById(int id) {
        return bookingRepository.existsById(id);
    }

    @Override
    public Booking findById (int id) {
        return bookingRepository.findById(id).get();
    }

    @Override
    public BookingDTO findByIdDto(int id) {
        return convertEntityToDto(bookingRepository.findById(id).get());
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingDTO save(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        return convertEntityToDto(savedBooking);
    }

    @Override
    public Booking convertRequestToEntity(BookingRequest bookingRequest, Booking booking) {
        booking.setTripPlacedTime(bookingRequest.getTripPlacedTime());
        booking.setTripScheduledTime(bookingRequest.getTripScheduledTime());
        booking.setTripStartTime(bookingRequest.getTripStartTime());
        booking.setTripFinishedTime(bookingRequest.getTripFinishedTime());
        booking.setTripCanceledTime(bookingRequest.getTripCanceledTime());
        booking.setPickUpLocation(bookingRequest.getPickUpLocation());
        booking.setDropOffLocation(bookingRequest.getDropOffLocation());
        booking.setStatus(publicEnum.BookingStatus.valueOf(bookingRequest.getStatus()));
        booking.setDistance(bookingRequest.getDistance());
        if (userService.existsById(bookingRequest.getUserId())) {
            booking.setUser(userService.findById(bookingRequest.getUserId()));
        }
        if (vehicleService.existsById(bookingRequest.getVehicleId())) {
            booking.setVehicle(vehicleService.findById(bookingRequest.getVehicleId()));
        }
        if (driverService.existsById(bookingRequest.getDriverId())) {
            booking.setDriver(driverService.findById(bookingRequest.getDriverId()));
        }
        return booking;
    }

    @Override
    public BookingDTO update(int id, BookingRequest bookingRequest) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            return save(convertRequestToEntity(bookingRequest, booking));
        }
        return null;
    }
}
