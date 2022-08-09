package com.thenuka.gocheetaproject.modals;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Vehicle> vehicles;
}
