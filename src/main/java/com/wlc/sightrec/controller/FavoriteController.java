package com.wlc.sightrec.controller;

import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Favorite;
import com.wlc.sightrec.service.FavoriteService;
import com.wlc.sightrec.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @RequestMapping(path = {"/favorites"}, method = {RequestMethod.GET})
    public JSONObject isExistInFavorite(@RequestParam("sightId") int sightId,
                                        @RequestParam("userId") int userId,
                                        HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject addToFavorite = new JSONObject();
        try {
            // 临时组成一个 Favorite
            Favorite favorite = new Favorite();
            favorite.setSightId(sightId);
            favorite.setUserId(userId);
            favorite.setStatus(0);
            Date date = new Date();
            favorite.setCreatedDate(date);
            boolean result = favoriteService.isExistInFavorite(favorite);
            addToFavorite.put("data", result);
            addToFavorite.put("meta", JsonUtil.getMeta("判断是否位于收藏夹成功", 200));
            return addToFavorite;
        } catch (Exception e) {
            addToFavorite.put("data", false);
            addToFavorite.put("meta", JsonUtil.getMeta("判断是否位于收藏夹失败", 400));
            return addToFavorite;
        }
    }

    @RequestMapping(path = {"/favorites/userId"}, method = {RequestMethod.GET})
    public JSONObject getUserFavorite(@RequestParam("userId") int userId,
                                      HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject addToFavorite = new JSONObject();
        try {
            List<Favorite> favoriteList = favoriteService.getUserFavorite(userId);
            addToFavorite.put("data", favoriteList);
            addToFavorite.put("meta", JsonUtil.getMeta("获取我的收藏成功", 200));
            return addToFavorite;
        } catch (Exception e) {
            addToFavorite.put("data", null);
            addToFavorite.put("meta", JsonUtil.getMeta("获取我的收藏失败", 400));
            return addToFavorite;
        }
    }

    @RequestMapping(path = {"/favorites"}, method = {RequestMethod.POST})
    public JSONObject addToFavorite(@RequestBody JSONObject favoriteJson,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject addToFavorite = new JSONObject();
        try {
            Favorite favorite = new Favorite();
            favorite.setSightId(favoriteJson.getInteger("sightId"));
            favorite.setUserId(favoriteJson.getInteger("userId"));
            favorite.setStatus(0);
            Date date = new Date();
            favorite.setCreatedDate(date);
            favoriteService.addToFavorite(favorite);
            addToFavorite.put("data", null);
            addToFavorite.put("meta", JsonUtil.getMeta("添加收藏成功", 200));
            return addToFavorite;
        } catch (Exception e) {
            addToFavorite.put("data", null);
            addToFavorite.put("meta", JsonUtil.getMeta(e.getMessage(), 400));
            return addToFavorite;
        }
    }

    @RequestMapping(path = {"/favorites/{sightId}/{userId}"}, method = {RequestMethod.DELETE})
    public JSONObject removeFromFavorite(@PathVariable("sightId") int sightId, @PathVariable("userId") int userId,
                                         HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject removeFromFavorite = new JSONObject();
        try {
            favoriteService.removeFromFavorite(sightId, userId);
            removeFromFavorite.put("data", null);
            removeFromFavorite.put("meta", JsonUtil.getMeta("取消收藏成功", 200));
            return removeFromFavorite;
        } catch (Exception e) {
            removeFromFavorite.put("data", null);
            removeFromFavorite.put("meta", JsonUtil.getMeta("取消收藏失败", 400));
            return removeFromFavorite;
        }
    }
}
