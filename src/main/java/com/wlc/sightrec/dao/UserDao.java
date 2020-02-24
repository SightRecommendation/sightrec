package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    int insertUser(User user);
    int deleteUserById(Integer id);
    int updateUser(User user);
    List<User> queryUser(String keyString, Integer pageNum, Integer pageSize);
    int userCount();

}
