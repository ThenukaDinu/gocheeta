package com.thenuka.gocheetaproject.modals;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String contactNo;
    private String address;

    @OneToMany(mappedBy = "branch")
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "branch")
    private Set<Driver> drivers;
}
