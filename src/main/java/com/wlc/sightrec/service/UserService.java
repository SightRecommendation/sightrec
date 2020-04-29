package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.User;

import java.util.Map;

public interface UserService {
    Boolean insertUser(User user);

    Boolean deleteUserById(Integer id);

    User queryUserById(Integer id);

    User queryUserByName(String name);

    Map<String, Object> queryUser(String keyString, Integer pageNum, Integer pageSize);

    Boolean updateUser(User user);

    Map<String, Object> login(User user);

    Map<String, Object> register(User user);

}
