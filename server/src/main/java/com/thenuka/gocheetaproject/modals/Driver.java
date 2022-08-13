package com.thenuka.gocheetaproject.modals;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String NIC;
    private String backupMobile;

    @OneToMany(mappedBy = "driver")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "driver")
    private Set<Vehicle> vehicles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "driver")
    private Set<Rating> ratings;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
