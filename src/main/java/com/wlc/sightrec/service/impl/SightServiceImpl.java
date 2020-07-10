package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.SightDao;
import com.wlc.sightrec.entity.Sight;
import com.wlc.sightrec.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SightServiceImpl implements SightService {

    @Autowired
    SightDao sightDao;

    @Override
    public Sight getSightById(int id) {
        return sightDao.selectById(id);
    }

    @Override
    public List<Sight> getSightsByName(String query) {
        return sightDao.selectByName(query);
    }

    @Override
    public List<Sight> getSightsByNameAndHeat(String query) {
        return sightDao.selectByNameAndHeat(query);
    }

    @Override
    public List<Sight> getSightsByNameAndPoint(String query) {
        return sightDao.selectByNameAndPoint(query);
    }

    @Override
    public List<Sight> getSightsByTag(String query) {
        return sightDao.selectByTag(query);
    }

    @Override
    public void deleteSight(int id) {
        try {
            int success = sightDao.updateStatus(id);
            if (success <= 0) {
                throw new RuntimeException("删除景点失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除景点失败：catch");
        }
    }

    @Override
    public int getSightCount() {
        return sightDao.getSightCount();
    }

    @Override
    public double getRating(int id) {
        return sightDao.getRating(id);
    }

    @Override
    public int getRatingFrequency(int id) {
        return sightDao.getRatingFrequency(id);
    }

    @Override
    public void updateRating(int id, double newRatingPoint) {
        try {
            int success = sightDao.updateRating(id, newRatingPoint);
            if (success <= 0) {
                throw new RuntimeException("修改评分次数失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("修改评分次数失败：catch");
        }
    }

    @Override
    public void modifySight(Sight changedSight) {
        try {
            int success = sightDao.updateSight(changedSight);
            if (success <= 0) {
                throw new RuntimeException("修改景点失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("修改景点失败：catch");
        }
    }

    @Override
    public void addRatingFrequency(int id) {
        try {
            int success = sightDao.updateRatingFrequency(id);
            if (success <= 0) {
                throw new RuntimeException("修改评分次数失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("修改评分次数失败：catch");
        }
    }


    @Override
    public void addSightHeat(int id) {
        try {
            int success = sightDao.updateHeat(id);
            if (success <= 0) {
                throw new RuntimeException("修改热度失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("修改热度失败：catch");
        }
    }
}
