package com.wlc.sightrec.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Comment;

import java.util.List;

public class JsonUtil {
    public static JSONObject getMeta(String msg, int status) {
        JSONObject metaJson = new JSONObject();
        metaJson.put("msg", msg);
        metaJson.put("status", status);
        return metaJson;
    }

    public static JSONObject getCommentData(int totalPage, int pageNum, List<Comment> commentList) {
        JSONObject dataJson = new JSONObject();
        dataJson.put("totalPage", totalPage);
        dataJson.put("pageNum", pageNum);
        JSONArray commentArrayJson = new JSONArray();
        commentArrayJson.addAll(commentList);
        dataJson.put("comment", commentArrayJson);
        return dataJson;
    }
}
