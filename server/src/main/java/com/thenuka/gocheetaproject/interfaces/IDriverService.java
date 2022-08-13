package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.DriverDTO;
import com.thenuka.gocheetaproject.modals.Driver;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.requests.DriverRequest;

import java.util.List;
import java.util.Set;

public interface IDriverService {
    DriverDTO findOne(int id);
    Driver findById(int id);
    boolean existsById(int id);
    DriverDTO save(Driver driver);
    DriverDTO update(int id, DriverRequest driverRequest);
    void delete(int id);
    List<DriverDTO> findAll();
    DriverDTO convertEntityToDto(Driver driver);
    Driver convertRequestToEntity(DriverRequest driverRequest, User user, Driver driver);
}
