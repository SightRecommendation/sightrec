package com.wlc.sightrec.service;

import java.util.List;

public interface RecommendationService {
    int doRecommendation();
    List<Integer> getRecommendation(Integer userId);
}
