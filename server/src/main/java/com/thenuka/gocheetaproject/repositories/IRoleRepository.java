package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
}
