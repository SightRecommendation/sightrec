package com.wlc.sightrec.controller;

import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Comment;
import com.wlc.sightrec.service.CommentService;
import com.wlc.sightrec.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/comments"}, method = {RequestMethod.GET})
    public JSONObject getComments(@RequestParam("query") String query,
                                  @RequestParam("pageNum") int pageNum,
                                  @RequestParam("pageSize") int pageSize,
                                  HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        // query 为搜索参数
        String auth = request.getHeader("Authorization");

        List<Comment> commentList;
        List<Comment> commentListByPage;
        JSONObject getComments = new JSONObject();
        try {
            // 模糊搜索，返回所有符合条件的评论
            query = "%" + query + "%";
            commentList = commentService.getCommentsByContent(query);
            // 根据 页码 和 每页评论数 分页
            int commentNum = commentList.size();
            int totalPage = (int) Math.ceil(commentNum * 1.0 / pageSize);
            int offset = pageSize * (pageNum - 1);
            commentListByPage = commentList.subList(offset, Math.min(commentNum, offset + pageSize));
            getComments.put("data", JsonUtil.getCommentData(totalPage, pageNum, commentNum, commentListByPage));
            getComments.put("meta", JsonUtil.getMeta("获取评论列表成功", 200));
            return getComments;
        } catch (Exception e) {
            getComments.put("data", null);
            getComments.put("meta", JsonUtil.getMeta("获取评论列表失败", 400));
            return getComments;
        }
    }

    @RequestMapping(path = {"/comments/sight"}, method = {RequestMethod.GET})
    public JSONObject getCommentsBySightId(@RequestParam("sightId") int sightId,
                                           @RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize,
                                           HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        List<Comment> commentList;
        List<Comment> commentListByPage;
        JSONObject getComments = new JSONObject();
        try {
            commentList = commentService.getCommentsBySight(sightId);
            // 根据 页码 和 每页评论数 分页
            int commentNum = commentList.size();
            int totalPage = (int) Math.ceil(commentNum * 1.0 / pageSize);
            int offset = pageSize * (pageNum - 1);
            commentListByPage = commentList.subList(offset, Math.min(commentNum, offset + pageSize));
            getComments.put("data", JsonUtil.getCommentData(totalPage, pageNum, commentNum, commentListByPage));
            getComments.put("meta", JsonUtil.getMeta("获取评论列表成功", 200));
            return getComments;
        } catch (Exception e) {
            getComments.put("data", null);
            getComments.put("meta", JsonUtil.getMeta("获取评论列表失败", 400));
            return getComments;
        }
    }

    @RequestMapping(path = {"/comments/user"}, method = {RequestMethod.GET})
    public JSONObject getCommentsByUserId(@RequestParam("userId") int userId,
                                           @RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize,
                                           HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        List<Comment> commentList;
        List<Comment> commentListByPage;
        JSONObject getComments = new JSONObject();
        try {
            commentList = commentService.getCommentsByUser(userId);
            // 根据 页码 和 每页评论数 分页
            int commentNum = commentList.size();
            int totalPage = (int) Math.ceil(commentNum * 1.0 / pageSize);
            int offset = pageSize * (pageNum - 1);
            commentListByPage = commentList.subList(offset, Math.min(commentNum, offset + pageSize));
            getComments.put("data", JsonUtil.getCommentData(totalPage, pageNum, commentNum, commentListByPage));
            getComments.put("meta", JsonUtil.getMeta("获取评论列表成功", 200));
            return getComments;
        } catch (Exception e) {
            getComments.put("data", null);
            getComments.put("meta", JsonUtil.getMeta("获取评论列表失败", 400));
            return getComments;
        }
    }

    @RequestMapping(path = {"/comments"}, method = {RequestMethod.POST})
    public JSONObject addComment(@RequestBody Comment comment,
                                 HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject addComment = new JSONObject();
        try {
            // 若没有 createdDate，说明是用户从景点详情页评论的，添加当前时间即可
            if (comment.getCreatedDate() == null) {
                Date date = new Date();
                comment.setCreatedDate(date);
            }
            commentService.addComment(comment);
            addComment.put("data", comment);
            addComment.put("meta", JsonUtil.getMeta("添加评论成功", 200));
            return addComment;
        } catch (Exception e) {
            addComment.put("data", null);
            addComment.put("meta", JsonUtil.getMeta("添加评论成功", 400));
            return addComment;
        }
    }

    @RequestMapping(path = {"/comments/{id}"}, method = {RequestMethod.GET})
    public JSONObject getComment(@PathVariable("id") int id,
                                 HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject getComment = new JSONObject();
        try {
            Comment commentById = commentService.getCommentById(id);
            getComment.put("data", commentById);
            getComment.put("meta", JsonUtil.getMeta("获取评论成功", 200));
            return getComment;
        } catch (Exception e) {
            getComment.put("data", null);
            getComment.put("meta", JsonUtil.getMeta("获取评论失败", 400));
            return getComment;
        }
    }

    @RequestMapping(path = {"/comments/{id}"}, method = {RequestMethod.PUT})
    public JSONObject modifyComment(@PathVariable("id") int id, @RequestBody JSONObject comment,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject modifyComment = new JSONObject();
        try {
            commentService.modifyComment(id, comment.getString("content"));
            modifyComment.put("data", null);
            modifyComment.put("meta", JsonUtil.getMeta("修改评论成功", 200));
            return modifyComment;
        } catch (Exception e) {
            modifyComment.put("data", null);
            modifyComment.put("meta", JsonUtil.getMeta("修改评论失败", 400));
            return modifyComment;
        }
    }

    @RequestMapping(path = {"/comments/{id}"}, method = {RequestMethod.DELETE})
    public JSONObject deleteComment(@PathVariable("id") int id,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject deleteComment = new JSONObject();
        try {
            commentService.deleteComment(id);
            deleteComment.put("data", null);
            deleteComment.put("meta", JsonUtil.getMeta("删除评论成功", 200));
            return deleteComment;
        } catch (Exception e) {
            deleteComment.put("data", null);
            deleteComment.put("meta", JsonUtil.getMeta("删除评论失败", 400));
            return deleteComment;
        }
    }
}
