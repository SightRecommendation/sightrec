package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.FavoriteDao;
import com.wlc.sightrec.entity.Favorite;
import com.wlc.sightrec.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteDao favoriteDao;

    @Override
    public void addToFavorite(Favorite favorite) {
        try {
            int success = favoriteDao.addToFavorite(favorite);
            if (success <= 0) {
                throw new RuntimeException("添加至收藏失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("添加至收藏失败：catch");
        }
    }

    @Override
    public void removeFromFavorite(int sightId, int userId) {
        try {
            int success = favoriteDao.removeFromFavorite(sightId, userId);
            if (success <= 0) {
                throw new RuntimeException("取消收藏失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("取消收藏失败：catch");
        }
    }
}
