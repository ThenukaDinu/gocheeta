package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.modals.Vehicle;
import com.thenuka.gocheetaproject.repositories.IVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public VehicleDTO update(Vehicle vehicle) {
        if(vehicleRepository.existsById(vehicle.getId())) {
            return save(vehicle);
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
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setVehicleType(vehicle.getVehicleType());
        vehicleDTO.setBrand(vehicle.getBrand());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setDriverId(vehicle.getDriver().getId());
        vehicleDTO.setBranchId(vehicle.getBranch().getId());
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
//            bookingDTO.setVehicleId(book.getVehicle().getId());
//            bookingDTO.setDriverId(book.getDriver().getId());
//            bookingDTO.setUserId(book.getUser().getId());
            bookingDTOS.add(bookingDTO);
        }
        vehicleDTO.setBookings(bookingDTOS);
        return vehicleDTO;
    }

    @Override
    public Vehicle convertDtoToEntity(VehicleDTO vehicleDTO) {
        return null;
    }
}
