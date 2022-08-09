package com.thenuka.gocheetaproject.modals;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nullable
    private String comment;
    private int ratingCount;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;
}
