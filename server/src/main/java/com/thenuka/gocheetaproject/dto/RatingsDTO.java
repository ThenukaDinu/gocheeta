package com.thenuka.gocheetaproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class RatingsDTO {
    private int id;
    private String comment;
    private int ratingCount;

    private int givenUserId;
    private int driverId;
}
