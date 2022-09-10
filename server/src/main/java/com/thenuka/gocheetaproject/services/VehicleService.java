package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BookingDTO;
import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.enums.publicEnum;
import com.thenuka.gocheetaproject.interfaces.*;
import com.thenuka.gocheetaproject.modals.*;
import com.thenuka.gocheetaproject.repositories.IVehicleRepository;
import com.thenuka.gocheetaproject.requests.VehicleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService implements IVehicleService {
    @Autowired
    private IVehicleRepository vehicleRepository;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IBranchService branchService;
    @Autowired
    private IDriverService driverService;

    @Override
    public VehicleDTO findOne(int id) {
        return convertEntityToDto(vehicleRepository.findById(id).get());
    }

    @Override
    public boolean existsById(int id) {
        return vehicleRepository.existsById(id);
    }

    @Override
    public Vehicle findById(int id) {
        return vehicleRepository.findById(id).get();
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
        if (StringUtils.hasText(vehicleRequest.getModel())) {
            vehicle.setModel(vehicleRequest.getModel());
        }
        if (StringUtils.hasText(vehicleRequest.getVehicleType())) {
            vehicle.setVehicleType(publicEnum.VehicleType.valueOf(vehicleRequest.getVehicleType()));
        }
        if (StringUtils.hasText(vehicleRequest.getBrand())) {
            vehicle.setBrand(vehicleRequest.getBrand());
        }
        if (StringUtils.hasText(vehicleRequest.getNumberPlateNo())) {
            vehicle.setNumberPlateNo(vehicleRequest.getNumberPlateNo());
        }
        if (categoryService.existsById(vehicleRequest.getCategoryId())) {
            Category category = categoryService.findById(vehicleRequest.getCategoryId());
            vehicle.setCategory(category);
        } else {
            vehicle.setCategory(null);
        }
        if (branchService.existsById(vehicleRequest.getBranchId())) {
            Branch branch = branchService.findById(vehicleRequest.getBranchId());
            vehicle.setBranch(branch);
        } else {
            vehicle.setBranch(null);
        }
        if (driverService.existsById(vehicleRequest.getDriverId())) {
            Driver driver = driverService.findById(vehicleRequest.getDriverId());
            vehicle.setDriver(driver);
        } else {
            vehicle.setDriver(null);
        }
        return vehicle;
    }
}
