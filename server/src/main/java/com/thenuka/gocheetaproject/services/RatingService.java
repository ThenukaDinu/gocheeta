package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.RatingsDTO;
import com.thenuka.gocheetaproject.interfaces.IBookingService;
import com.thenuka.gocheetaproject.interfaces.IDriverService;
import com.thenuka.gocheetaproject.interfaces.IRatingService;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.modals.Rating;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.repositories.IDriverRepository;
import com.thenuka.gocheetaproject.repositories.IRatingRepository;
import com.thenuka.gocheetaproject.repositories.IUserRepository;
import com.thenuka.gocheetaproject.requests.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingService implements IRatingService {

    @Autowired
    private IRatingRepository ratingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDriverService driverService;
    @Autowired
    private IBookingService bookingService;

    @Override
    public RatingsDTO findOne(int id) {
        return convertEntityToDto(ratingRepository.findById(id).get());
    }

    @Override
    public RatingsDTO save(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        return convertEntityToDto(savedRating);
    }

    @Override
    public RatingsDTO update(int id, RatingRequest ratingRequest) {
        if (driverService.existsById(ratingRequest.getDriverId())) {
            Rating rating = ratingRepository.findById(id).get();
            return save(convertRequestToEntity(ratingRequest, rating));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public List<RatingsDTO> findAll() {
        return ratingRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RatingsDTO convertEntityToDto(Rating rating) {
        RatingsDTO ratingsDTO = new RatingsDTO();
        ratingsDTO.setId(rating.getId());
        ratingsDTO.setComment(rating.getComment());
        ratingsDTO.setRatingCount(rating.getRatingCount());
        if (rating.getDriver() != null) ratingsDTO.setDriverId(rating.getDriver().getId());
        if(rating.getUser() != null)  ratingsDTO.setGivenUserId(rating.getUser().getId());
        return ratingsDTO;
    }

    @Override
    public Rating convertRequestToEntity(RatingRequest ratingRequest, Rating rating) {
        rating.setComment(ratingRequest.getComment());
        rating.setRatingCount(ratingRequest.getRatingCount());
        Object principals = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principals).getUsername();
        if (username != null) {
            User user = userService.findByUsername(username);
            if (user != null) rating.setUser(user);
        }
        if (driverService.existsById(ratingRequest.getDriverId())) {
            rating.setDriver(driverService.findById(ratingRequest.getDriverId()));
        }
        if (bookingService.existsById(ratingRequest.getBookingId())) {
            rating.setBooking(bookingService.findById(ratingRequest.getBookingId()));
        }
        return rating;
    }
}
