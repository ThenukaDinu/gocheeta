package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
}
