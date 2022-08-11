package com.thenuka.gocheetaproject.dto;

import com.thenuka.gocheetaproject.enums.publicEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class VehicleDTO {
    private int id;
    private String model;
    private publicEnum.VehicleType vehicleType;
    private String brand;
    private int branchId;
    private int driverId;
    private int categoryId;
    private ArrayList<BookingDTO> bookings;
}
