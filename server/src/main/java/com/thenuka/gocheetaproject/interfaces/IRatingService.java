package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.RatingsDTO;
import com.thenuka.gocheetaproject.modals.Rating;
import com.thenuka.gocheetaproject.requests.RatingRequest;

import java.util.List;

public interface IRatingService {
    RatingsDTO findOne(int id);
    RatingsDTO save(Rating rating);
    RatingsDTO update(int id, RatingRequest ratingRequest);
    void delete(int id);
    List<RatingsDTO> findAll();
    RatingsDTO convertEntityToDto(Rating rating);
    Rating convertRequestToEntity(RatingRequest ratingRequest, Rating rating);
}
