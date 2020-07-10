package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Sight;

import java.util.List;

public interface RecommendationService {
    int doRecommendation();

    List<Sight> getRecommendation(Integer userId);
}
