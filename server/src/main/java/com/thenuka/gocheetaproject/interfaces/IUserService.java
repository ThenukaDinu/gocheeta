package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.UserDto;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.modals.User;

import java.util.List;

public interface IUserService {

    UserRoleDTO getUser(int userId);
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}
