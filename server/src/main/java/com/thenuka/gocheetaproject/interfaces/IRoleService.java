package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.RoleDTO;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.requests.RoleRequest;

import java.util.List;

public interface IRoleService {
    Role findByName(String name);

    boolean existsById(int id);

    RoleDTO findById(int id);

    List<RoleDTO> findAll();

    RoleDTO save(Role role);

    void deleteById(int id);

    RoleDTO update(int id, RoleRequest roleRequest);

    RoleDTO convertEntityToDto(Role role);

    Role convertRequestToEntity(RoleRequest roleRequest, Role role);
}
