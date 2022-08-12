package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.enums.publicEnum;
import com.thenuka.gocheetaproject.interfaces.IBookingService;
import com.thenuka.gocheetaproject.interfaces.ICategoryService;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import com.thenuka.gocheetaproject.modals.*;
import com.thenuka.gocheetaproject.repositories.IBranchRepository;
import com.thenuka.gocheetaproject.repositories.ICategoryRepository;
import com.thenuka.gocheetaproject.repositories.IDriverRepository;
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
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IBranchRepository branchRepository;
    @Autowired
    private IDriverRepository driverRepository;

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
        vehicleDTO.setNumberPlateNo(vehicle.getNumberPlateNo());
        if (vehicle.getDriver() != null) vehicleDTO.setDriverId(vehicle.getDriver().getId());
        if (vehicle.getBranch() != null) vehicleDTO.setBranchId(vehicle.getBranch().getId());
        if (vehicle.getCategory() != null) vehicleDTO.setCategoryId(vehicle.getCategory().getId());
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<BookingDTO>();
        for (Booking book : vehicle.getBookings()) {
            BookingDTO bookingDTO = bookingService.convertEntityToDto(book);
            if (bookingDTO != null) bookingDTOS.add(bookingDTO);
        }
        vehicleDTO.setBookings(bookingDTOS);
        return vehicleDTO;
    }

    @Override
    public Vehicle convertRequestToEntity(VehicleRequest vehicleRequest, Vehicle vehicle) {
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setVehicleType(publicEnum.VehicleType.valueOf(vehicleRequest.getVehicleType()));
        vehicle.setBrand(vehicleRequest.getBrand());
        vehicle.setNumberPlateNo(vehicleRequest.getNumberPlateNo());
        if (categoryRepository.existsById(vehicleRequest.getCategoryId())) {
            Category category = categoryRepository.findById(vehicleRequest.getCategoryId()).get();
            vehicle.setCategory(category);
        }
        if (branchRepository.existsById(vehicleRequest.getBranchId())) {
            Branch branch = branchRepository.findById(vehicleRequest.getBranchId()).get();
            vehicle.setBranch(branch);
        }
        if (driverRepository.existsById(vehicleRequest.getDriverId())) {
            Driver driver = driverRepository.findById(vehicleRequest.getDriverId()).get();
            vehicle.setDriver(driver);
        }
        return vehicle;
    }
}
