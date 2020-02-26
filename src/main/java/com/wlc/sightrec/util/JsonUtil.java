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
        for (Comment comment : commentList) {
            JSONObject commentJson = new JSONObject();
            commentJson.put("id", comment.getId());
            commentJson.put("content", comment.getContent());
            commentJson.put("userId", comment.getUserId());
            commentJson.put("sightId", comment.getSightId());
            commentJson.put("createdDate", comment.getCreatedDate());
            commentJson.put("status", comment.getStatus());
            commentArrayJson.add(commentJson);
        }
        dataJson.put("comment", commentArrayJson);
        return dataJson;
    }
}
