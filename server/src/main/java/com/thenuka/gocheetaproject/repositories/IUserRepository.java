package com.thenuka.gocheetaproject.repositories;

import com.thenuka.gocheetaproject.modals.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
