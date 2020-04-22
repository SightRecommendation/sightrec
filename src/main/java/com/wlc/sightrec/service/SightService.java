package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Sight;

import java.util.List;

public interface SightService {
    Sight getSightById(int id);
    List<Sight> getSightsByName(String query);
    void deleteSight(int id);
    void modifySight(Sight changedSight);
}
