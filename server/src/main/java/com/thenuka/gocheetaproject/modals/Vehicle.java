package com.thenuka.gocheetaproject.modals;

import com.thenuka.gocheetaproject.enums.publicEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private publicEnum.VehicleType vehicleType;
    @Nullable
    private String brand;

    @ManyToOne
    @JoinColumn(name = "branchId")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private  Category category;

    @OneToMany
    @JoinColumn(name = "vehicle")
    private Set<Booking> bookings;
}
