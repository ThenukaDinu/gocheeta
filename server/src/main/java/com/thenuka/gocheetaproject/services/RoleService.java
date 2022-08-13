package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.RoleDTO;
import com.thenuka.gocheetaproject.interfaces.IRoleService;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.repositories.IRoleRepository;
import com.thenuka.gocheetaproject.requests.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "roleService")
@Transactional
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }

    @Override
    public boolean existsById(int id) {
        return roleRepository.existsById(id);
    }

    @Override
    public RoleDTO findById(int id) {
        return convertEntityToDto(roleRepository.findById(id).get());
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO save(Role role) {
        return convertEntityToDto(roleRepository.save(role));
    }

    @Override
    public void deleteById(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDTO update(int id, RoleRequest roleRequest) {
        if (roleRepository.existsById(id)) {
            Role role = roleRepository.findById(id).get();
            return save(convertRequestToEntity(roleRequest, role));
        }
        return null;
    }

    @Override
    public RoleDTO convertEntityToDto (Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(role.getId());
        roleDTO.setRoleName(role.getName());
        roleDTO.setDescription(role.getDescription());
        return roleDTO;
    }

    @Override
    public Role convertRequestToEntity(RoleRequest roleRequest, Role role) {
        role.setDescription(roleRequest.getDescription());
        role.setName(roleRequest.getName());
        return role;
    }
}
