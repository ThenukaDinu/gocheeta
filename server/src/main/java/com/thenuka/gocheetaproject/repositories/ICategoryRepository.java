package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
