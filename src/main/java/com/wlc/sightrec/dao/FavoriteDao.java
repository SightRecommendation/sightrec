package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.Favorite;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FavoriteDao {
    String TABLE_NAME = "favorite ";
    String INSERT_FIELDS = "user_id, sight_id, created_date, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{sightId},#{createdDate},0)"})
    int addToFavorite(Favorite favorite);

    @Update({"update ", TABLE_NAME,
            "set status=1 where sight_id=#{sightId} and user_id=#{userId}"})
    int removeFromFavorite(@Param("sightId") int sightId, @Param("userId") int userId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and sight_id=#{sightId} and user_id=#{userId}"})
    List<Favorite> isExistInFavorite(Favorite favorite);
}
