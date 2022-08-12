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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class BranchService implements IBranchService {

    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IDriverService driverService;

    @Override
    public BranchDTO convertEntityToDto(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setId(branchDTO.getId());
        branchDTO.setAddress(branchDTO.getAddress());
        branchDTO.setName(branch.getName());
        branchDTO.setContactNo(branchDTO.getContactNo());

        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : branch.getVehicles()) {
            VehicleDTO vehicleDTO = vehicleService.convertEntityToDto(vehicle);
            if (vehicleDTO != null) vehicleDTOS.add(vehicleDTO);
        }
        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();
        for (Driver driver : branch.getDrivers()) {
            DriverDTO driverDTO = driverService.convertEntityToDto(driver);
            if (driverDTO != null) driverDTOS.add(driverDTO);
        }
        branchDTO.setVehicles(vehicleDTOS);
        branchDTO.setDrivers(driverDTOS);

        return branchDTO;
    }
}
