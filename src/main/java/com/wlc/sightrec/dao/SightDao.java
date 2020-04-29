package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.Sight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SightDao {
    String TABLE_NAME = "qunar_sight ";
    String INSERT_FIELDS = "name, province, city, location, level, point, " +
            "description, introduction, heat, image_url, subject, coordinate, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and id=#{id}"})
    Sight selectById(@Param("id") int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and name like #{query} order by id asc"})
    List<Sight> selectByName(@Param("query") String query);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and name like #{query} order by heat desc"})
    List<Sight> selectByNameAndHeat(@Param("query") String query);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and subject like #{query}"})
    List<Sight> selectByTag(@Param("query") String query);

    @Update({"update ", TABLE_NAME,
            "set status=1 where id=#{id}"})
    int updateStatus(@Param("id") int id);

    @Update({"update ", TABLE_NAME,
            "set name=#{name}, province=#{province}, city=#{city}, location=#{location}, " +
                    "level=#{level}, point=#{point}, " +
                    "description=#{description}, introduction=#{introduction}, heat=#{heat}, " +
                    "image_url=#{imageUrl}, subject=#{subject}, coordinate=#{coordinate} where id=#{id}"})
    int updateSight(Sight sight);
}
