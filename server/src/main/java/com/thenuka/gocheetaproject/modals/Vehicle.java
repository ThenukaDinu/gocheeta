package com.thenuka.gocheetaproject.modals;

import com.thenuka.gocheetaproject.enums.publicEnum;

import javax.persistence.*;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private publicEnum.VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "branchId")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "driver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "category")
    private  Category category;
}
