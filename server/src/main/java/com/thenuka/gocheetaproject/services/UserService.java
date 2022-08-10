package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.RoleDTO;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserRoleDTO getUser(int userId) {
        UserRoleDTO userRoleDTO = userRepository.findById(userId)
                .map(this::convertEntityToDto)
                .get();

        return userRoleDTO;
    }

    private UserRoleDTO convertEntityToDto (User user) {
        if (user == null) { return null; }
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(user.getId());
        userRoleDTO.setAge(user.getAge());
        userRoleDTO.setEmail(user.getEmail());
        userRoleDTO.setFirstName(user.getFirstName());
        userRoleDTO.setLastName(user.getLastName());
        userRoleDTO.setFullName(user.getFullName());
        userRoleDTO.setMobile(user.getMobile());
        ArrayList<RoleDTO> roleDTOS = new ArrayList<RoleDTO>();
        for (Role role : user.getRoles()) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleId(role.getId());
            roleDTO.setRoleName(role.getName());
            roleDTO.setUserId(user.getId());
            roleDTOS.add(roleDTO);
        }
        userRoleDTO.setRoles(roleDTOS);
        return userRoleDTO;
    }

}
