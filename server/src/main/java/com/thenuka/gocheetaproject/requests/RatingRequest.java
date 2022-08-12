package com.thenuka.gocheetaproject.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    private String comment;
    private int ratingCount;

    private int driverId;
    private int ratingGivenUserId;
}
