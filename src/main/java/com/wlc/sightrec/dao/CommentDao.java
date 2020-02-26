package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {
    String TABLE_NAME = "comment ";
    String INSERT_FIELDS = "content, user_id, sight_id, created_date, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{content},#{userId},#{sightId},#{createdDate},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and sight_id=#{sightId} order by id desc"})
    List<Comment> selectBySight(@Param("sightId") int sightId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 order by id desc limit #{limit} offset #{offset}"})
    List<Comment> selectByPage(@Param("limit") int limit, @Param("offset") int offset);

    @Select({"select count(id) from ", TABLE_NAME,
            " where status=0 and sight_id=#{sightId}"})
    int getCommentCountbySight(@Param("sightId") int sightId);

    @Select({"select count(id) from ", TABLE_NAME, " where status=0"})
    int getCommentCount();

    /**
     * 删除评论
     * @param id 评论的 id
     * */
    @Update({"update ", TABLE_NAME,
            "set status=1 where id=#{id}"})
    void updateStatus(@Param("id") int id);
}
