package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Favorite;

public interface FavoriteService {
    boolean isExistInFavorite(Favorite favorite);

    boolean addToFavorite(Favorite favorite);

    boolean removeFromFavorite(int sightId, int userId);
}
