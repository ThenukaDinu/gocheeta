package com.thenuka.gocheetaproject.modals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String NIC;
    private String Address;
    private String backupMobile;

    @OneToMany(mappedBy = "driver")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "driver")
    private Set<Vehicle> vehicles;

    @ManyToOne
    @JoinColumn(name = "branchId")
    private Branch branch;

    @OneToMany(mappedBy = "driver")
    private Set<Rating> ratings;
}
