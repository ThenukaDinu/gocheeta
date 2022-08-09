package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRatingRepository extends JpaRepository<Rating, Integer> {
}
