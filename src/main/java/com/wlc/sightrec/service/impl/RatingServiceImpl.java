package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.RatingDao;
import com.wlc.sightrec.entity.Rating;
import com.wlc.sightrec.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingDao ratingDao;

    @Override
    public int getRating(int sightId, int userId) {
        try {
            Rating rating = ratingDao.getRating(sightId, userId);
            if (rating == null) {
                throw new RuntimeException("评分不存在");
            } else {
                return rating.getRating();
            }
        } catch (Exception e) {
            throw new RuntimeException("获取评分失败：" + e.getMessage());
        }
    }

    @Override
    public boolean addRating(Rating rating) {
        try {
            Rating rating1 = ratingDao.getRating(rating.getSightId(), rating.getUserId());
            if (rating1 != null) {
                throw new RuntimeException("评分已存在");
            } else {
                int success = ratingDao.addRating(rating);
                if (success > 0) {
                    return true;
                } else {
                    throw new RuntimeException("数据库操作失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取评分失败：" + e.getMessage());
        }
    }

    @Override
    public boolean changeRating(int rating, int sightId, int userId) {
        try {
            Rating rating1 = ratingDao.getRating(sightId, userId);
            if (rating1 == null) {
                throw new RuntimeException("评分不存在");
            } else {
                int success = ratingDao.changeRating(rating, sightId, userId);
                if (success > 0) {
                    return true;
                } else {
                    throw new RuntimeException("数据库操作失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取评分失败：" + e.getMessage());
        }
    }
}
