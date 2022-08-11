package com.thenuka.gocheetaproject.modals;

import com.thenuka.gocheetaproject.enums.publicEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nullable
    private double price;
    @Nullable
    private LocalDateTime tripPlacedTime;
    @Nullable
    private LocalDateTime tripScheduledTime;
    @Nullable
    private LocalDateTime tripStartTime;
    @Nullable
    private LocalDateTime tripFinishedTime;
    @Nullable
    private LocalDateTime tripCanceledTime;
    private String pickUpLocation;
    private String dropOffLocation;
    private publicEnum.BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicleId")
    private Vehicle vehicle;
}
