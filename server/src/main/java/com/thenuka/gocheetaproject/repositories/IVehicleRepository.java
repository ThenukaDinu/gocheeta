package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Integer> {
}
