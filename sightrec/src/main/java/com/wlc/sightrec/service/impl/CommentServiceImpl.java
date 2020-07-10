package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.CommentDao;
import com.wlc.sightrec.entity.Comment;
import com.wlc.sightrec.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public void addComment(Comment comment) {
        try {
            int success = commentDao.addComment(comment);
            if (success <= 0) {
                throw new RuntimeException("添加评论失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("添加评论失败：catch");
        }
    }

    @Override
    public List<Comment> getCommentsBySight(int sightId) {
        return commentDao.selectBySight(sightId);
    }

    @Override
    public List<Comment> getCommentsByUser(int userId) {
        return commentDao.selectByUser(userId);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentDao.selectById(id);
    }

    @Override
    public List<Comment> getCommentsByContent(String query) {
        return commentDao.selectByContent(query);
    }

    @Override
    public int getCommentCountbySight(int sightId) {
        return commentDao.getCommentCountbySight(sightId);
    }

    @Override
    public int getCommentCount() {
        return commentDao.getCommentCount();
    }

    @Override
    public void deleteComment(int id) {
        try {
            int success = commentDao.updateStatus(id);
            if (success <= 0) {
                throw new RuntimeException("删除评论失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除评论失败：catch");
        }
    }

    @Override
    public void modifyComment(int id, String content) {
        try {
            int success = commentDao.updateComment(id, content);
            ;
            if (success <= 0) {
                throw new RuntimeException("修改评论失败：数据库 return");
            }
        } catch (Exception e) {
            throw new RuntimeException("修改评论失败：catch");
        }
    }
}
