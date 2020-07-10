package com.wlc.sightrec.controller;

import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Rating;
import com.wlc.sightrec.service.RatingService;
import com.wlc.sightrec.service.SightService;
import com.wlc.sightrec.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    SightService sightService;

    @RequestMapping(path = {"/ratings"}, method = {RequestMethod.GET})
    public JSONObject getRating(@RequestParam("sightId") int sightId,
                                @RequestParam("userId") int userId,
                                HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject getRating = new JSONObject();
        try {
            int result = 0;
            result = ratingService.getRating(sightId, userId);
            getRating.put("data", result);
            getRating.put("meta", JsonUtil.getMeta("获取评分成功", 200));
            return getRating;
        } catch (Exception e) {
            getRating.put("data", 0);
            getRating.put("meta", JsonUtil.getMeta(e.getMessage(), 400));
            return getRating;
        }
    }

    @RequestMapping(path = {"/ratings"}, method = {RequestMethod.POST})
    public JSONObject addRating(@RequestBody JSONObject ratingJson,
                                HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject addRating = new JSONObject();
        try {
            int sightId = ratingJson.getInteger("sightId");
            int ratingPoint = ratingJson.getInteger("rating");

            Rating rating = new Rating();
            rating.setSightId(sightId);
            rating.setUserId(ratingJson.getInteger("userId"));
            rating.setRating(ratingPoint);
            rating.setStatus(0);
            Date date = new Date();
            rating.setCreatedDate(date);
            ratingService.addRating(rating);

            int ratingFrequency = sightService.getRatingFrequency(sightId);
            double totalRating = sightService.getRating(sightId) * ratingFrequency + ratingPoint;
            double newRatingPoint = Math.round(totalRating / (ratingFrequency + 1) * 10) / 10;
            sightService.updateRating(sightId, newRatingPoint);
            sightService.addRatingFrequency(sightId);

            addRating.put("data", null);
            addRating.put("meta", JsonUtil.getMeta("添加评分成功", 200));
            return addRating;
        } catch (Exception e) {
            addRating.put("data", null);
            addRating.put("meta", JsonUtil.getMeta(e.getMessage(), 400));
            return addRating;
        }
    }

    @RequestMapping(path = {"/ratings"}, method = {RequestMethod.PUT})
    public JSONObject changeRating(@RequestBody JSONObject ratingJson,
                                   HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject changeRating = new JSONObject();
        try {
            int sightId = ratingJson.getInteger("sightId");
            int ratingPoint = ratingJson.getInteger("rating");

            ratingService.changeRating(ratingPoint, sightId, ratingJson.getInteger("userId"));

            int ratingFrequency = sightService.getRatingFrequency(sightId);
            double totalRating = sightService.getRating(sightId) * ratingFrequency + ratingPoint;
            double newRatingPoint = Math.round(totalRating / ratingFrequency * 10) / 10;
            sightService.updateRating(sightId, newRatingPoint);

            changeRating.put("data", null);
            changeRating.put("meta", JsonUtil.getMeta("修改评分成功", 200));
            return changeRating;
        } catch (Exception e) {
            changeRating.put("data", null);
            changeRating.put("meta", JsonUtil.getMeta(e.getMessage(), 400));
            return changeRating;
        }
    }
}
