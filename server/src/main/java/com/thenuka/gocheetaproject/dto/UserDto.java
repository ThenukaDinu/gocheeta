package com.thenuka.gocheetaproject.dto;

import com.thenuka.gocheetaproject.modals.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String name;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    public User getUserFromDto(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user.setMobile(mobile);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address);
        return user;
    }
}
