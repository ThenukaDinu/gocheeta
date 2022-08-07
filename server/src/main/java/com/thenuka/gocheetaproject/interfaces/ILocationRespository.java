package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.modals.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRespository extends JpaRepository<Location, Integer> {
}
