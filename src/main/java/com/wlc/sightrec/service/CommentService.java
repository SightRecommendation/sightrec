package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getCommentsBySight(int sightId);
    Comment getCommentById(int id);
    List<Comment> getCommentsByContent(String query);
    int getCommentCountbySight(int sightId);
    int getCommentCount();
    void deleteComment(int id);
    void modifyComment(int id, String content);
}
