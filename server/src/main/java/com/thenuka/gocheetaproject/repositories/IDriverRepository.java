package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepository extends JpaRepository<Driver, Integer> {
}
