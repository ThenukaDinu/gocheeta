package com.thenuka.gocheetaproject.modals;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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
    private String username;
    private String password;
    private String address;
    @Nullable
    private String avatar;

    @Transient
    public String getPhotosImagePath() {
        if (avatar == null || id == 0) return null;

        return "/static/user-photos/" + id + "/" + avatar;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "user")
    private Set<Rating> ratings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Driver driver;

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
