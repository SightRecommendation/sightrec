package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.User;
import java.util.Map;

public interface UserService {
    Boolean insertUser(User user);
    Boolean deleteUserById(Integer id);
    User queryUserById(Integer id);
    Map<String,Object> queryUser(String keyString, Integer pageNum, Integer pageSize);
    Boolean updateUser(User user);

}
