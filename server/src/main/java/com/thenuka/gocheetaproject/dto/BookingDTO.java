package com.thenuka.gocheetaproject.dto;

import com.thenuka.gocheetaproject.enums.publicEnum;
import com.thenuka.gocheetaproject.modals.Driver;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.modals.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {
    private int id;
    private double price;
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
