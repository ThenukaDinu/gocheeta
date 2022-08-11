package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
}
