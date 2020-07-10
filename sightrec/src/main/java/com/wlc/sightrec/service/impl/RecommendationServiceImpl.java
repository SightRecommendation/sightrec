package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.RatingDao;
import com.wlc.sightrec.dao.RecommendationDao;
import com.wlc.sightrec.dao.SightDao;
import com.wlc.sightrec.entity.Rating;
import com.wlc.sightrec.entity.Recommendation;
import com.wlc.sightrec.entity.Sight;
import com.wlc.sightrec.service.RecommendationService;
import com.wlc.sightrec.service.SightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.mutable.WrappedArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    RatingDao ratingDao;

    @Autowired
    RecommendationDao recommendationDao;

    @Autowired
    SightDao sightDao;

    @Autowired
    SightService sightService;

    @Override
    public int doRecommendation() {
        List<Rating> allRatings = ratingDao.getAllRating();

        SparkConf conf = new SparkConf().setMaster("local").setAppName("Recommendation");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);

        JavaRDD<Rating> ratingsRDD = jsc.parallelize(allRatings);

        Dataset<Row> ratings = sqlContext.createDataFrame(ratingsRDD, Rating.class);
        Dataset<Row>[] splits = ratings.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> training = splits[0];
        Dataset<Row> test = splits[1];

        // Build the recommendation model using ALS on the training data
        ALS als = new ALS()
                .setMaxIter(5)
                .setRegParam(0.01)
                .setUserCol("userId")
                .setItemCol("sightId")
                .setRatingCol("rating");
        ALSModel model = als.fit(training);

        // Evaluate the model by computing the RMSE on the test data
        // Note we set cold start strategy to 'drop' to ensure we don't get NaN evaluation metrics
        model.setColdStartStrategy("drop");
        Dataset<Row> predictions = model.transform(test);

        RegressionEvaluator evaluator = new RegressionEvaluator()
                .setMetricName("rmse")
                .setLabelCol("rating")
                .setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predictions);
        System.out.println("Root-mean-square error = " + rmse);

        // Generate top 10 movie recommendations for each user
        Dataset<Row> userRecs = model.recommendForAllUsers(10);
        userRecs.show();

        List<Row> list = userRecs.javaRDD().collect();
        for (Row row : list) {
            String result = "";
            log.info(row.get(0).toString());
            log.info(row.get(1).toString());
            WrappedArray wrappedArray = (WrappedArray) row.get(1);
            for (int j = 0; j < wrappedArray.size(); j++) {
                String str = wrappedArray.apply(j).toString();
                result += str.split(",")[0];
            }
            Integer userId = Integer.parseInt(row.get(0).toString());
            Recommendation recommendation = recommendationDao.getRecommendation(userId);
            if (recommendation == null) {
                recommendationDao.addRecommendation(userId, result);
            } else {
                recommendationDao.changeRecommendation(userId, result);
            }

        }

        jsc.stop();
        return 0;
    }

    @Override
    public List<Sight> getRecommendation(Integer userId) {
        try {
            Recommendation recommendation = recommendationDao.getRecommendation(userId);
            List<Sight> sights = new ArrayList<>();
            if (recommendation == null) {
                int sightCount = sightService.getSightCount();
                Random rand = new Random();
                for (int i = 0; i < 5 * 20; i++) {
                    if (sights.size() >= 5) {
                        break;
                    }
                    int tempSightId = rand.nextInt(sightCount);
                    Sight tempSight = sightService.getSightById(tempSightId);
                    if (tempSight.getPoint() >= 4.9 && tempSight.getHeat() >= 3000) {
                        sights.add(tempSight);
                    }
                }
                while (sights.size() < 5) {
                    sights.add(sightService.getSightById(rand.nextInt(sightCount)));
                }
            } else {
                String itemIds = recommendation.getItemIds();
                List<String> ids = Arrays.asList(itemIds.split("\\["));
                System.out.println(ids);
                List<String> newIds = new ArrayList<>();
                for (String id : ids) {
                    if (!"".equals(id)) {
                        newIds.add(id);
                    }
                }
                List<Integer> rand = new Random().ints(0, 9).distinct().limit(5).boxed().collect(Collectors.toList());
                for (Integer i : rand) {
                    Integer idNum = Integer.parseInt(newIds.get(i));
                    System.out.println(idNum);
                    sights.add(sightDao.selectById(idNum));
                }
            }
            return sights;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
