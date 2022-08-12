package com.thenuka.gocheetaproject.modals;

import com.thenuka.gocheetaproject.enums.publicEnum;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
