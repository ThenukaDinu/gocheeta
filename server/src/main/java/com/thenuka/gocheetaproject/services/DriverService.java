package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.*;
import com.thenuka.gocheetaproject.interfaces.*;
import com.thenuka.gocheetaproject.modals.*;
import com.thenuka.gocheetaproject.repositories.IDriverRepository;
import com.thenuka.gocheetaproject.requests.DriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DriverService implements IDriverService {

    @Autowired
    private IDriverRepository driverRepository;
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
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public DriverDTO findOne(int id) {
        return convertEntityToDto(driverRepository.findById(id).get());
    }

    @Override
    public Driver findById(int id) {
        return driverRepository.findById(id).get();
    }

    @Override
    public boolean existsById(int id) {
        return driverRepository.existsById(id);
    }

    @Override
    public DriverDTO save(Driver driver) {
        User user = userService.save(driver.getUser());
        driver.setUser(user);
        Driver savedDriver = driverRepository.save(driver);
        return convertEntityToDto(savedDriver);
    }

    @Override
    public DriverDTO update(int id, DriverRequest driverRequest) {
        if (driverRepository.existsById(id)) {
            Driver driver = driverRepository.findById(id).get();
            if (userService.existsById(driver.getUser().getId())) {
                User user = userService.findById(driver.getUser().getId());
                Driver uDriver = convertRequestToEntity(driverRequest, user, driver);
                user.setDriver(uDriver);
                userService.save(user);
                return convertEntityToDto(uDriver);
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        Driver driver = driverRepository.findById(id).get();
        if (driver != null) {
            int relatedUserId = driver.getUser().getId();
            userService.deleteById(relatedUserId);
        }
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

        driverDTO.setDriverId(driver.getId());
        driverDTO.setNIC(driver.getNIC());
        driverDTO.setBackupMobile(driver.getBackupMobile());

        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        if (driver.getBookings() != null) {
            for (Booking book : driver.getBookings()) {
                BookingDTO bookingDTO = bookingService.convertEntityToDto(book);
                if (bookingDTO != null) bookingDTOS.add(bookingDTO);
            }
        }
        driverDTO.setBookings(bookingDTOS);

        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();
        if (driver.getVehicles() != null) {
            for (Vehicle vehicle : driver.getVehicles()) {
                VehicleDTO vehicleDTO = vehicleService.convertEntityToDto(vehicle);
                if (vehicleDTO != null) vehicleDTOS.add(vehicleDTO);
            }
        }
        driverDTO.setVehicles(vehicleDTOS);

        ArrayList<RatingsDTO> ratingsDTOS = new ArrayList<>();
        if (driver.getRatings() != null) {
            for (Rating rating : driver.getRatings()) {
                RatingsDTO ratingsDTO = ratingService.convertEntityToDto(rating);
            }
        }
        driverDTO.setRatings(ratingsDTOS);

        if (driver.getBranch() != null) {
            BranchDTO branchDTO = branchService.convertEntityToDto(driver.getBranch());
            if (branchDTO != null) driverDTO.setBranch(branchDTO);
        }
        return driverDTO;
    }

    @Override
    public Driver convertRequestToEntity(DriverRequest driverRequest, User nUser, Driver driver) {
        nUser.setPassword(driverRequest.getPassword());
        nUser.setEmail(driverRequest.getUsername());
        nUser.setUsername(driverRequest.getUsername());
        nUser.setAddress(driverRequest.getAddress());
        nUser.setDateOfBirth(driverRequest.getDateOfBirth());
        nUser.setMobile(driverRequest.getMobile());
        nUser.setFirstName(driverRequest.getFirstName());
        nUser.setLastName(driverRequest.getLastName());
        driver.setUser(nUser);

        driver.setNIC(driverRequest.getNIC());
        driver.setBackupMobile(driverRequest.getBackupMobile());
        if (branchService.existsById(driverRequest.getBranchId())) {
            driver.setBranch(branchService.findById(driverRequest.getBranchId()));
        }
        Set<Vehicle> vehiclesList = new HashSet<>();
        if (driverRequest.getVehicles() != null) {
            for (Integer vehicleId : driverRequest.getVehicles()) {
                if (vehicleService.existsById(vehicleId)) {
                    vehiclesList.add(vehicleService.findById(vehicleId));
                }
            }
        }
        driver.setVehicles(vehiclesList);
        return driver;
    }
}
