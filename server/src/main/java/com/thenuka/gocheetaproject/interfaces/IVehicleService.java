package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.modals.Vehicle;

import java.util.List;

public interface IVehicleService {
    VehicleDTO findOne(int id);
    VehicleDTO save(Vehicle vehicle);
    VehicleDTO update(Vehicle vehicle);
    void delete(int id);
    List<VehicleDTO> findAll();
    VehicleDTO convertEntityToDto(Vehicle vehicle);
    Vehicle convertDtoToEntity(VehicleDTO vehicleDTO);
}
