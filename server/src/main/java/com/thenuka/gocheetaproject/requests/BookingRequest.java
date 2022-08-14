package com.thenuka.gocheetaproject.requests;

import com.thenuka.gocheetaproject.dto.RatingsDTO;
import com.thenuka.gocheetaproject.enums.publicEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class BookingRequest {
    private LocalDateTime tripPlacedTime;
    private LocalDateTime tripScheduledTime;
    private LocalDateTime tripStartTime;
    private LocalDateTime tripFinishedTime;
    private LocalDateTime tripCanceledTime;
    private String pickUpLocation;
    private String dropOffLocation;
    private String status;

    private int driverId;
    private int userId;
    private int vehicleId;
}
