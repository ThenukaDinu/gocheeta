package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.enums.publicEnum;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.modals.Vehicle;
import com.thenuka.gocheetaproject.repositories.IVehicleRepository;
import com.thenuka.gocheetaproject.requests.VehicleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService implements IVehicleService {
    @Autowired
    private IVehicleRepository vehicleRepository;

    @Override
    public VehicleDTO findOne(int id) {
        return convertEntityToDto(vehicleRepository.findById(id).get());
    }

    @Override
    public VehicleDTO save(Vehicle vehicle) {
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertEntityToDto(savedVehicle);
    }

    @Override
    public VehicleDTO update(int id, VehicleRequest vehicleRequest) {
        if(vehicleRepository.existsById(id)) {
            Vehicle vehicle = vehicleRepository.findById(id).get();
            return save(convertRequestToEntity(vehicleRequest, vehicle));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<VehicleDTO> findAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO convertEntityToDto(Vehicle vehicle) {
        if (vehicle == null) return null;
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setVehicleType(vehicle.getVehicleType());
        vehicleDTO.setBrand(vehicle.getBrand());
        vehicleDTO.setModel(vehicle.getModel());
        if (vehicle.getDriver() != null) vehicleDTO.setDriverId(vehicle.getDriver().getId());
        if (vehicle.getBranch() != null) vehicleDTO.setBranchId(vehicle.getBranch().getId());
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<BookingDTO>();
        for (Booking book : vehicle.getBookings()) {
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
            bookingDTOS.add(bookingDTO);
        }
        vehicleDTO.setBookings(bookingDTOS);
        return vehicleDTO;
    }

    @Override
    public Vehicle convertRequestToEntity(VehicleRequest vehicleRequest, Vehicle vehicle) {
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setVehicleType(publicEnum.VehicleType.valueOf(vehicleRequest.getVehicleType()));
        vehicle.setBrand(vehicleRequest.getBrand());
        // need to implement.
        /*
        * category
        * branch
        * driver
        * */
        return vehicle;
    }
}
