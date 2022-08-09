package com.thenuka.gocheetaproject.modals;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String NIC;
    private String Address;
    private String mobile;

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
