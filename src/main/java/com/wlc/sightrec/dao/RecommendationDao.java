package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.Rating;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecommendationDao {
    String TABLE_NAME = "recommendation";
    String FIELDS = "user_id,item_ids";

    @Insert({"insert into ", TABLE_NAME, "(", FIELDS,
            ") values (#{userId},#{sightId},#{rating},#{createdDate},0)"})
    int addRecommendation(@Param("userId") Integer userId, @Param("itemIds") String itemIds);

    @Update({"update ", TABLE_NAME,
            "set item_ids=#{itemIds} where user_id=#{userId}"})
    int changeRecommendation(@Param("userId") Integer userId, @Param("itemIds") String itemIds);

    @Select({"select ", FIELDS, " from ", TABLE_NAME,
            " where user_id=#{userId}"})
    Rating getRecommendation(@Param("userId") Integer userId);
}
