package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.*;
import com.thenuka.gocheetaproject.interfaces.*;
import com.thenuka.gocheetaproject.modals.*;
import com.thenuka.gocheetaproject.repositories.IBranchRepository;
import com.thenuka.gocheetaproject.repositories.IDriverRepository;
import com.thenuka.gocheetaproject.repositories.IVehicleRepository;
import com.thenuka.gocheetaproject.requests.DriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@Transactional
public class DriverService implements IDriverService {

    @Autowired
    private IDriverRepository driverRepository;
    @Autowired
    private IBranchRepository branchRepository;
    @Autowired
    private IVehicleRepository vehicleRepository;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IRatingService ratingService;
    @Autowired
    private IBranchService branchService;
    @Autowired
    private IUserService userService;

    @Override
    public DriverDTO findOne(int id) {
        return convertEntityToDto(driverRepository.findById(id).get());
    }

    @Override
    public DriverDTO save(Driver driver) {
        Driver savedDriver = driverRepository.save(driver);
        return convertEntityToDto(driver);
    }

    @Override
    public DriverDTO update(int id, DriverRequest driverRequest) {
        if (driverRepository.existsById(id)) {
            Driver driver = driverRepository.findById(id).get();
            return save(convertRequestToEntity(driverRequest, driver));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        driverRepository.deleteById(id);
    }

    @Override
    public List<DriverDTO> findAll() {
        return driverRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDTO convertEntityToDto(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();
        User relatedUser = driver.getUser();
        if (relatedUser == null) return null;
        driverDTO.setUserId(relatedUser.getId());
        driverDTO.setUsername(relatedUser.getUsername());
        driverDTO.setEmail(relatedUser.getEmail());
        driverDTO.setMobile(relatedUser.getMobile());
        driverDTO.setFirstName(relatedUser.getFirstName());
        driverDTO.setLastName(relatedUser.getLastName());
        driverDTO.setDateOfBirth(relatedUser.getDateOfBirth());
        driverDTO.setAddress(relatedUser.getAddress());

        driverDTO.setDriverId(driverDTO.getDriverId());
        driverDTO.setNIC(driverDTO.getNIC());
        driverDTO.setBackupMobile(driver.getBackupMobile());

        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        for (Booking book : driver.getBookings()) {
            BookingDTO bookingDTO = bookingService.convertEntityToDto(book);
            if (bookingDTO != null) bookingDTOS.add(bookingDTO);
        }
        driverDTO.setBookings(bookingDTOS);

        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : driver.getVehicles()) {
            VehicleDTO vehicleDTO = vehicleService.convertEntityToDto(vehicle);
            if (vehicleDTO != null) vehicleDTOS.add(vehicleDTO);
        }
        driverDTO.setVehicles(vehicleDTOS);

        ArrayList<RatingsDTO> ratingsDTOS = new ArrayList<>();
        for (Rating rating : driver.getRatings()) {
            RatingsDTO ratingsDTO = ratingService.convertEntityToDto(rating);
        }
        driverDTO.setRatings(ratingsDTOS);

        BranchDTO branchDTO = branchService.convertEntityToDto(driver.getBranch());
        if (branchDTO != null) driverDTO.setBranch(branchDTO);

        return driverDTO;
    }

    @Override
    public Driver convertRequestToEntity(DriverRequest driverRequest, Driver driver) {
        driver.setNIC(driverRequest.getNIC());
        driver.setBackupMobile(driverRequest.getBackupMobile());
        if (branchRepository.existsById(driverRequest.getBranchId())) {
            driver.setBranch(branchRepository.findById(driverRequest.getBranchId()).get());
        }
        Set<Vehicle> vehiclesList = new HashSet<>();
        for (Integer vehicleId : driverRequest.getVehicles()) {
            if (vehicleRepository.existsById(vehicleId)) {
                vehiclesList.add(vehicleRepository.findById(vehicleId).get());
            }
        }
        driver.setVehicles(vehiclesList);
        return driver;
    }
}
