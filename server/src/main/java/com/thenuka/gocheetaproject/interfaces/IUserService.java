package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.UserDto;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.requests.UserRoleRequest;

import java.util.List;

public interface IUserService {

    UserRoleDTO getUser(int userId);

    UserRoleDTO update(int id, UserDto userRequest);

    User save(UserDto user);
    User save(User user);

    User findByUsername(String username);

    List<User> findAll();

    UserRoleDTO assignRoles (int id, UserRoleRequest userRoleRequest);

    User findOne(String username);

    boolean existsById(int id);

    void deleteById(int id);

    User findById(int id);

    UserRoleDTO convertEntityToDto(User user);
    User setInitialRoles (User user);

    User convertRequestToEntity(UserDto userRequest, User user);
}
