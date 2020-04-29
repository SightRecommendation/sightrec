package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Favorite;

public interface FavoriteService {
    void addToFavorite(Favorite favorite);

    void removeFromFavorite(int sightId, int userId);
}
