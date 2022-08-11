package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.interfaces.IRoleService;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }
}
