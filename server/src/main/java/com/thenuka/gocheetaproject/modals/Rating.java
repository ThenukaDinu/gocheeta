package com.thenuka.gocheetaproject.modals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "ratings")
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

    @OneToOne
    @JoinColumn(name = "ratingGivenUserId")
    private User user;
}
