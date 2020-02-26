package com.wlc.sightrec.controller;

import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Comment;
import com.wlc.sightrec.service.impl.CommentServiceImpl;
import com.wlc.sightrec.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @RequestMapping(path = {"/comments"}, method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject getComments(@RequestBody JSONObject queryInfo,
                                  HttpServletRequest request, HttpServletResponse response) {
        // keys: query pageNum pagesize
        // token 验证没写
        // query 为搜索参数，没写
        String auth = request.getHeader("Authorization");

        String query = queryInfo.getString("query");
        int pageNum = Integer.parseInt(queryInfo.getString("pageNum"));
        int pageSize = Integer.parseInt(queryInfo.getString("pageSize"));
        int totalPage = (int) Math.ceil(commentService.getCommentCount() * 1.0 / pageSize);
        List<Comment> commentList;

        JSONObject comments = new JSONObject();
        int statusCode = response.getStatus();
        if (statusCode == 200) {
            commentList = commentService.getCommentsByPage(pageSize, pageNum);
            comments.put("data", JsonUtil.getCommentData(totalPage, pageNum, commentList));
            comments.put("meta", JsonUtil.getMeta("获取评论成功", statusCode));
        } else {
            comments.put("data", null);
            comments.put("meta", JsonUtil.getMeta("获取评论失败", statusCode));
        }
        return comments;
    }

    @RequestMapping(path = {"/comments/{id}"}, method = {RequestMethod.DELETE})
    public JSONObject deleteComment(@PathVariable("id") int id,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject deleteComment = new JSONObject();
        int statusCode = response.getStatus();
        if (statusCode == 200) {
            commentService.deleteComment(id);
            deleteComment.put("data", null);
            deleteComment.put("meta", JsonUtil.getMeta("删除评论成功", statusCode));
        } else {
            deleteComment.put("data", null);
            deleteComment.put("meta", JsonUtil.getMeta("删除评论失败", statusCode));
        }
        return deleteComment;
    }
}
