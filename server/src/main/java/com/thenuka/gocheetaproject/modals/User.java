package com.thenuka.gocheetaproject.modals;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;
    private int age;
    private String mobile;
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String firstName, String lastName, String fullName, LocalDate dateOfBirth, int age, String mobile, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    @OneToMany
    Set<UserRole> userRole;

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    @OneToMany
    @JoinColumn(name = "user", nullable = false)
    private Set<Booking> bookings;

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        if (this.dateOfBirth != null) {
            return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

//    public void setAge(int age) {
//        this.age = age;
//    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
