package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<Booking, Integer> {
}
