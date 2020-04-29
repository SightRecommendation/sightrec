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
}
