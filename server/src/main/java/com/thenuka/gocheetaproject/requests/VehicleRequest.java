package com.thenuka.gocheetaproject.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class VehicleRequest {
    private String vehicleType;
    private String brand;
    private String model;
    private String numberPlateNo;
    @Nullable
    private int branchId;
    @Nullable
    private int driverId;
    @Nullable
    private int categoryId;
}
