package com.thenuka.gocheetaproject.modals;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDateTime dateOfCreated;

    public Role() {
    }

    public Role(int id, String name, LocalDateTime dateOfCreated) {
        this.id = id;
        this.name = name;
        this.dateOfCreated = dateOfCreated;
    }

    @OneToMany
    Set<UserRole> userRole;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }
}
