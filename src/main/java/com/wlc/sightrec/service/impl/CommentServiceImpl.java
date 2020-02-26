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
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public List<Comment> getCommentsBySight(int sightId) {
        return commentDao.selectBySight(sightId);
    }

    @Override
    public List<Comment> getCommentsByPage(int pageSize, int pageNum) {
        int offset = pageSize * (pageNum - 1);
        return commentDao.selectByPage(pageSize, offset);
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
        commentDao.updateStatus(id);
    }
}
