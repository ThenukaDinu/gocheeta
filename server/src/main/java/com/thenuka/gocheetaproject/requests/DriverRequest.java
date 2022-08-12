package com.thenuka.gocheetaproject.requests;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DriverRequest {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String name;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    private int driverId;
    private String NIC;
    private String backupMobile;

    private int branchId;
    private List<Integer> vehicles;
}
