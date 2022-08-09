package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchRepository extends JpaRepository<Branch, Integer> {
}
