package com.wlc.sightrec;

import com.wlc.sightrec.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecommendationTests {
    @Autowired
    RecommendationService recommendationService;

    @Test
    void contextLoads() {
        recommendationService.doRecommendation();
    }
}
