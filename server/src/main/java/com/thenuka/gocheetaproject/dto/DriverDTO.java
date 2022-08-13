package com.thenuka.gocheetaproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class DriverDTO {
    private int userId;
    private String username;
    private String email;
    private String mobile;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    private int driverId;
    private String NIC;
    private String backupMobile;

    private ArrayList<BookingDTO> bookings;
    private ArrayList<VehicleDTO> vehicles;
    private ArrayList<RatingsDTO> ratings;
    private BranchDTO branch;
}
