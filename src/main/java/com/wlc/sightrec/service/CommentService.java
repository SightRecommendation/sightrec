package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);
    List<Comment> getCommentsBySight(int sightId);
    List<Comment> getCommentsByPage(int pageSize, int pageNum);
    int getCommentCountbySight(int sightId);
    int getCommentCount();
    void deleteComment(int id);
}
