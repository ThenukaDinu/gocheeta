package com.thenuka.gocheetaproject.modals;

import com.thenuka.gocheetaproject.enums.publicEnum;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nullable
    private double price;
    @Nullable
    private LocalDateTime tripStartTime;
    private LocalDateTime tripPlacedTime;
    @Nullable
    private LocalDateTime tripCanceledTime;
    private String pickUp;
    private String dropOff;
    private publicEnum.BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "driverId", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
