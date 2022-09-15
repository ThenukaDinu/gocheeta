package com.thenuka.gocheetaproject.dto;

import com.thenuka.gocheetaproject.enums.publicEnum;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {
    private int id;
    private double price;
    private double distance;
    private double duration;
    private LocalDateTime tripPlacedTime;
    private LocalDateTime tripScheduledTime;
    private LocalDateTime tripStartTime;
    private LocalDateTime tripFinishedTime;
    private LocalDateTime tripCanceledTime;
    private String pickUpLocation;
    private String dropOffLocation;
    private publicEnum.BookingStatus status;
    private int driverId;
    private int userId;
    private int vehicleId;
    private int ratingId;
}
