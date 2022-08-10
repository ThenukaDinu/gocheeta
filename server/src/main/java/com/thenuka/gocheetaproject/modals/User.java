package com.thenuka.gocheetaproject.modals;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Transient
    private String fullName;
    private LocalDate dateOfBirth;
    @Transient
    private int age;
    private String mobile;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name="user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    Set<Role> roles;

//    public Set<Booking> getBookings() {
//        return bookings;
//    }

//    public void setBookings(Set<Booking> bookings) {
//        this.bookings = bookings;
//    }

//    @OneToMany
//    @JoinColumn(name = "user", nullable = false)
//    private Set<Booking> bookings;

    @PostLoad
    private void onLoad() {
        this.fullName = this.firstName + " " + this.lastName ;
        if (this.dateOfBirth != null) {
            this.age = Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        } else {
            this.age = 0;
        }
    }
}
