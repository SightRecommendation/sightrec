package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.FavoriteDao;
import com.wlc.sightrec.entity.Favorite;
import com.wlc.sightrec.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteDao favoriteDao;

    @Override
    public boolean isExistInFavorite(Favorite favorite) {
        try {
            // 判断是否已收藏
            List<Favorite> getFromFavorite = favoriteDao.isExistInFavorite(favorite);
            for (Favorite favorite1 : getFromFavorite) {
                if (favorite1.getStatus() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean addToFavorite(Favorite favorite) {
        try {
            // 判断是否已收藏
            List<Favorite> getFromFavorite = favoriteDao.isExistInFavorite(favorite);
            for (Favorite favorite1 : getFromFavorite) {
                if (favorite1.getStatus() == 0) {
                    throw new RuntimeException("景点已收藏");
                }
            }
            int success = favoriteDao.addToFavorite(favorite);
            if (success > 0) {
                return true;
            } else {
                throw new RuntimeException("数据库操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean removeFromFavorite(int sightId, int userId) {
        try {
            int success = favoriteDao.removeFromFavorite(sightId, userId);
            if (success > 0) {
                return true;
            } else {
                throw new RuntimeException("数据库操作失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("取消收藏失败：catch");
        }
    }

    @Override
    public List<Favorite> getUserFavorite(int userId) {
        try {
            return favoriteDao.getUserFavorite(userId);
        } catch (Exception e) {
            throw new RuntimeException("获取我的收藏失败");
        }
    }
}
