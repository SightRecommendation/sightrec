package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.Rating;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RatingDao {
    String TABLE_NAME = "rating ";
    String INSERT_FIELDS = "user_id, sight_id, rating, created_date, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{sightId},#{rating},#{createdDate},0)"})
    int addRating(Rating rating);

    @Update({"update ", TABLE_NAME,
            "set rating=#{rating} where sight_id=#{sightId} and user_id=#{userId}"})
    int changeRating(@Param("rating") int rating, @Param("sightId") int sightId, @Param("userId") int userId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and sight_id=#{sightId} and user_id=#{userId}"})
    Rating getRating(@Param("sightId") int sightId, @Param("userId") int userId);
}
