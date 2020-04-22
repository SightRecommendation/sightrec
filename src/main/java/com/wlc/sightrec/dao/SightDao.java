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
    String TABLE_NAME = "sight ";
    String INSERT_FIELDS = "name, province, city, location, image_url, point, level, " +
            "description, introduction, heat, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and id=#{id}"})
    Sight selectById(@Param("id") int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where status=0 and name like #{query} order by id asc"})
    List<Sight> selectByName(@Param("query") String query);

    @Update({"update ", TABLE_NAME,
            "set status=1 where id=#{id}"})
    int updateStatus(@Param("id") int id);

    @Update({"update ", TABLE_NAME,
            "set name=#{name}, province=#{province}, city=#{city}, location=#{location}, " +
                    "image_url=#{imageUrl}, point=#{point}, level=#{level}, " +
                    "description=#{description}, introduction=#{introduction}, heat=#{heat} where id=#{id}"})
    int updateSight(Sight sight);
}
