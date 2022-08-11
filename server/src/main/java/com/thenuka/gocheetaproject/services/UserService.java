package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.RoleDTO;
import com.thenuka.gocheetaproject.dto.UserDto;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserRoleDTO getUser(int userId) {
        UserRoleDTO userRoleDTO = userRepository.findById(userId)
                .map(this::convertEntityToDto)
                .get();

        return userRoleDTO;
    }

    @Override
    public User save(UserDto user) {

        User nUser = user.getUserFromDto();
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepository.save(nUser);
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserRoleDTO convertEntityToDto (User user) {
        if (user == null) { return null; }
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(user.getId());
        userRoleDTO.setAge(user.getAge());
        userRoleDTO.setEmail(user.getEmail());
        userRoleDTO.setFirstName(user.getFirstName());
        userRoleDTO.setLastName(user.getLastName());
        userRoleDTO.setFullName(user.getFullName());
        userRoleDTO.setMobile(user.getMobile());
        userRoleDTO.setAddress(user.getAddress());
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
