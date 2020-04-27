package com.wlc.sightrec.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Comment;
import com.wlc.sightrec.entity.Sight;

import java.util.List;

public class JsonUtil {
    public static JSONObject getMeta(String msg, int status) {
        JSONObject metaJson = new JSONObject();
        metaJson.put("msg", msg);
        metaJson.put("status", status);
        return metaJson;
    }

    public static JSONObject getCommentData(int totalPage, int pageNum, int commentNum,
                                            List<Comment> commentList) {
        JSONObject dataJson = new JSONObject();
        dataJson.put("totalPage", totalPage);
        dataJson.put("commentNum", commentNum);
        dataJson.put("pageNum", pageNum);
        JSONArray commentArrayJson = new JSONArray();
        commentArrayJson.addAll(commentList);
        dataJson.put("comments", commentArrayJson);
        return dataJson;
    }

    public static JSONObject getSightData(int totalPage, int pageNum, int sightNum,
                                          List<Sight> sightList) {
        JSONObject dataJson = new JSONObject();
        dataJson.put("totalPage", totalPage);
        dataJson.put("sightNum", sightNum);
        dataJson.put("pageNum", pageNum);
        JSONArray sightArrayJson = new JSONArray();
        sightArrayJson.addAll(sightList);
        dataJson.put("sights", sightArrayJson);
        return dataJson;
    }

    public static JSONArray getSearchedSightData(List<Sight> sightList) {
        JSONArray sightArrayJson = new JSONArray();
        for (Sight sight : sightList) {
            JSONObject dataJson = new JSONObject();
            dataJson.put("value", sight.getName());
            dataJson.put("address", sight.getCity());
            dataJson.put("id", sight.getId());
            sightArrayJson.add(dataJson);
        }
        return sightArrayJson;
    }
}
