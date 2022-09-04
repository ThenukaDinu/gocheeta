package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.BranchDTO;
import com.thenuka.gocheetaproject.dto.DriverDTO;
import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.interfaces.IBranchService;
import com.thenuka.gocheetaproject.interfaces.IDriverService;
import com.thenuka.gocheetaproject.interfaces.IVehicleService;
import com.thenuka.gocheetaproject.modals.Branch;
import com.thenuka.gocheetaproject.modals.Driver;
import com.thenuka.gocheetaproject.modals.Vehicle;
import com.thenuka.gocheetaproject.repositories.IBranchRepository;
import com.thenuka.gocheetaproject.requests.BranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BranchService implements IBranchService {

    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IDriverService driverService;
    @Autowired
    private IBranchRepository branchRepository;

    @Override
    public boolean existsById(int id) {
        return branchRepository.existsById(id);
    }

    @Override
    public Branch findById(int id) {
        return branchRepository.findById(id).get();
    }

    @Override
    public BranchDTO findOne(int id) {
        return convertEntityToDto(branchRepository.findById(id).get());
    }

    @Override
    public BranchDTO save(Branch branch) {
        Branch savedBranch = branchRepository.save(branch);
//        for (Vehicle vehicle : savedBranch.getVehicles()) {
//            vehicle.setBranch(savedBranch);
//            vehicleService.save(vehicle);
//        }
//        for (Driver driver : savedBranch.getDrivers()) {
//            driver.setBranch(savedBranch);
//            driverService.save(driver);
//        }
        return convertEntityToDto(savedBranch);
    }

    @Override
    public BranchDTO update(int id, BranchRequest branchRequest) {
        if (branchRepository.existsById(id)) {
            Branch branch = branchRepository.findById(id).get();
            return save(convertRequestToEntity(branchRequest, branch));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        branchRepository.deleteById(id);
    }

    @Override
    public List<BranchDTO> findAll() {
        return branchRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDTO convertEntityToDto(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setId(branch.getId());
        branchDTO.setAddress(branch.getAddress());
        branchDTO.setName(branch.getName());
        branchDTO.setContactNo(branch.getContactNo());

        if (branch.getVehicles() != null) {
            ArrayList<Integer> VehiclesList = new ArrayList<>();
            for (Vehicle vehicle : branch.getVehicles()) {
                if (vehicle != null) VehiclesList.add(vehicle.getId());
            }
            branchDTO.setVehicles(VehiclesList);
        }
        if (branch.getDrivers() != null) {
            ArrayList<Integer> driversList = new ArrayList<>();
            for (Driver driver : branch.getDrivers()) {
                if (driver != null) {
                    if (driver != null) driversList.add(driver.getId());
                }
            }
            branchDTO.setDrivers(driversList);
        }
        return branchDTO;
    }

    @Override
    public Branch convertRequestToEntity(BranchRequest branchRequest, Branch branch) {
        branch.setAddress(branchRequest.getAddress());
        branch.setName(branchRequest.getName());
        branch.setContactNo(branchRequest.getContactNo());

        if (branchRequest.getDrivers() != null) {
            Set<Driver> driverSet = new HashSet<>();
            for (Integer driverId : branchRequest.getDrivers()) {
                if (driverService.existsById(driverId)) driverSet.add(driverService.findById(driverId));
            }
            branch.setDrivers(driverSet);
        }
        if (branchRequest.getVehicles() != null) {
            Set<Vehicle> vehicleSet = new HashSet<>();
            for (Integer vehicleId : branchRequest.getVehicles()) {
                if (vehicleService.existsById(vehicleId)) vehicleSet.add(vehicleService.findById(vehicleId));
            }
            branch.setVehicles(vehicleSet);
        }
        return branch;
    }
}
