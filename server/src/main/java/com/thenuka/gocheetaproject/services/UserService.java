package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.RoleDTO;
import com.thenuka.gocheetaproject.dto.UserDto;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.modals.Role;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.repositories.IUserRepository;
import com.thenuka.gocheetaproject.requests.UserRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
@Transactional
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

    @Override
    public User findByUsername (String username) {
        User user = userRepository.findByUsername(username);
        return user;
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
    public UserRoleDTO update(int id, UserDto userRequest) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            User updatedUser = convertRequestToEntity(userRequest, user);
            return convertEntityToDto(userRepository.save(updatedUser));
        }
        return null;
    }

    @Override
    public User save(UserDto user) {
        User nUser = user.getUserFromDto();
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        setInitialRoles(nUser);
        return userRepository.save(nUser);
    }

    @Override
    public User save (User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        setInitialRoles(user);
        return userRepository.save(user);
    }

    @Override
    public User setInitialRoles (User user) {
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(user.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        return user;
    }

    @Override
    public UserRoleDTO assignRoles(int id, UserRoleRequest userRoleRequest) {
        if (userRepository.existsById(id) == false) return null;
        User user = userRepository.findById(id).get();
        Set<Role> roleSet = new HashSet<>();
        if (roleService.findByName("USER") != null) {
            roleSet.add(roleService.findByName("USER"));
        }
        for (String roleName : userRoleRequest.getRolesList()) {
            if (roleService.findByName(roleName) != null) {
                roleSet.add(roleService.findByName(roleName));
            }
        }
        user.setRoles(roleSet);
        return convertEntityToDto(userRepository.save(user));
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
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
        userRoleDTO.setAvatarUrl(user.getPhotosImagePath());
        ArrayList<RoleDTO> roleDTOS = new ArrayList<RoleDTO>();
        for (Role role : user.getRoles()) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleId(role.getId());
            roleDTO.setRoleName(role.getName());
            roleDTO.setDescription(role.getDescription());
            roleDTOS.add(roleDTO);
        }
        userRoleDTO.setRoles(roleDTOS);
        return userRoleDTO;
    }

    @Override
    public User convertRequestToEntity(UserDto userRequest, User user) {
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getUsername());
        user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
        user.setMobile(userRequest.getMobile());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setAddress(userRequest.getAddress());
        return user;
    }

    @Override
    public UserRoleDTO saveAvatar(int id, String name) {
        User user = userRepository.findById(id).get();
        user.setAvatar(name);
        User savedUser = userRepository.save(user);
        return convertEntityToDto(savedUser);
    }

}
