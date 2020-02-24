package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.UserDao;
import com.wlc.sightrec.entity.User;
import com.wlc.sightrec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Boolean insertUser(User user) {
        try{
            int success = userDao.insertUser(user);
            if (success > 0) {
                return true;
            } else {
                throw new RuntimeException("新增用户失败！");
            }
        }catch (Exception e){
            throw new RuntimeException("新增用户失败！");
        }

    }
}
