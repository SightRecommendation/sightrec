package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Sight;

import java.util.List;

public interface SightService {
    Sight getSightById(int id);

    List<Sight> getSightsByName(String query);

    List<Sight> getSightsByNameAndHeat(String query);

    List<Sight> getSightsByNameAndPoint(String query);

    List<Sight> getSightsByTag(String query);

    void deleteSight(int id);

    int getSightCount();

    void modifySight(Sight changedSight);
}
