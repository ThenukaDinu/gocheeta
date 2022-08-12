package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.RatingsDTO;
import com.thenuka.gocheetaproject.interfaces.IRatingService;
import com.thenuka.gocheetaproject.modals.Rating;
import com.thenuka.gocheetaproject.requests.RatingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RatingService implements IRatingService {
    @Override
    public RatingsDTO findOne(int id) {
        return null;
    }

    @Override
    public RatingsDTO save(Rating driver) {
        return null;
    }

    @Override
    public RatingsDTO update(int id, RatingRequest ratingRequest) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<RatingsDTO> findAll() {
        return null;
    }

    @Override
    public RatingsDTO convertEntityToDto(Rating rating) {
        return null;
    }

    @Override
    public Rating convertRequestToEntity(RatingRequest ratingRequest, Rating rating) {
        return null;
    }
}
