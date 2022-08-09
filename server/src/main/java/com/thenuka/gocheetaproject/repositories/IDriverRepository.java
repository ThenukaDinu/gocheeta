package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDriverRepository extends JpaRepository<Driver, Integer> {
}
