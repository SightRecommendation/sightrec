package com.wlc.sightrec;

import com.wlc.sightrec.dao.CommentDao;
import com.wlc.sightrec.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class CommentTests {
    @Autowired
    CommentDao commentDao;

    @Test
    void contextLoads() {
        Comment comment = new Comment();
        comment.setContent("第三条评论");
        comment.setCreatedDate(new Date());
        comment.setSightId(1);
        comment.setUserId(1);
        commentDao.addComment(comment);
//        commentDao.updateStatus(1);
    }
}
