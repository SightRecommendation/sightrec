package com.wlc.sightrec;

import com.wlc.sightrec.dao.RatingDao;
import com.wlc.sightrec.entity.Rating;
import com.wlc.sightrec.service.RecommendationService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.List;

@SpringBootTest
public class RecommendationTests {
    @Autowired
    RecommendationService recommendationService;

    @Test
    void contextLoads() {

        recommendationService.doRecommendation();
    }
}
