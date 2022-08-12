package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.VehicleDTO;
import com.thenuka.gocheetaproject.modals.Vehicle;
import com.thenuka.gocheetaproject.requests.VehicleRequest;

import java.util.List;

public interface IVehicleService {
    VehicleDTO findOne(int id);
    VehicleDTO save(Vehicle vehicle);
    VehicleDTO update(int id, VehicleRequest vehicle);
    void delete(int id);
    List<VehicleDTO> findAll();
    VehicleDTO convertEntityToDto(Vehicle vehicle);
    Vehicle convertRequestToEntity(VehicleRequest vehicleRequest, Vehicle vehicle);
}
