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
        for (int i = 1; i < 14; i++) {
            Comment comment = new Comment();
            StringBuilder content = new StringBuilder("第 ");
            content.append(i);
            content.append(" 条评论");
            comment.setContent(String.valueOf(content));
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            comment.setCreatedDate(date);
            comment.setSightId(1);
            comment.setUserId(i % 3);
            commentDao.addComment(comment);

        }
//        commentDao.updateStatus(1);
    }
}
