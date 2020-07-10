package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.RatingDao;
import com.wlc.sightrec.dao.RecommendationDao;
import com.wlc.sightrec.entity.Rating;
import com.wlc.sightrec.service.RecommendationService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    RatingDao ratingDao;

    @Override
    public int doRecommendation() {
        List<Rating> allRatings = ratingDao.getAllRating();
//        SparkSession spark = SparkSession
//                .builder()
//                .appName("Recommendation")
//                .getOrCreate();

        SparkConf conf = new SparkConf().setMaster("local").setAppName("Recommendation");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);

        JavaRDD<Rating> ratingsRDD = jsc.parallelize(allRatings);


//        // $example on$
//        JavaRDD<Rating> ratingsRDD = spark
//                .read().textFile("data/mllib/als/sample_movielens_ratings.txt").javaRDD()
//                .map(Rating::parseRating);
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
        // Generate top 10 user recommendations for each movie
        Dataset<Row> movieRecs = model.recommendForAllItems(10);

        // Generate top 10 movie recommendations for a specified set of users
        Dataset<Row> users = ratings.select(als.getUserCol()).distinct().limit(3);
        Dataset<Row> userSubsetRecs = model.recommendForUserSubset(users, 10);
        // Generate top 10 user recommendations for a specified set of movies
        Dataset<Row> movies = ratings.select(als.getItemCol()).distinct().limit(3);
        Dataset<Row> movieSubSetRecs = model.recommendForItemSubset(movies, 10);
        // $example off$
        userRecs.show();


        jsc.stop();
        return 0;
    }

    @Override
    public List<Integer> getRecommendation(Integer userId) {
        return null;
    }
}
