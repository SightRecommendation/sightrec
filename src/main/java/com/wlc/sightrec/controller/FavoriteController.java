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

@RestController
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @RequestMapping(path = {"/favorites"}, method = {RequestMethod.POST})
    public JSONObject addToFavorite(@RequestBody JSONObject favoriteJson,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject addToFavorite = new JSONObject();
        try {
            // 判断重复收藏没写
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
            addToFavorite.put("meta", JsonUtil.getMeta("添加收藏失败", 400));
            return addToFavorite;
        }
    }

    @RequestMapping(path = {"/favorites"}, method = {RequestMethod.DELETE})
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
