package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Rating;

public interface RatingService {
    int getRating(int sightId, int userId);

    boolean addRating(Rating rating);

    boolean changeRating(int rating, int sightId, int userId);
}
