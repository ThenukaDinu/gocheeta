package com.thenuka.gocheetaproject.dto;

import com.thenuka.gocheetaproject.modals.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
public class UserRoleDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private int age;
    private String mobile;
    private String email;
    private String address;
    private ArrayList<RoleDTO> roles;
}
