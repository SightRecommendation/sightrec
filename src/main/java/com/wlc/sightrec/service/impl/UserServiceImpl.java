package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.UserDao;
import com.wlc.sightrec.entity.User;
import com.wlc.sightrec.service.UserService;
import com.wlc.sightrec.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public Boolean insertUser(User user) {
        try {
            User foundUser = userDao.queryUserByName(user.getName());
            if (foundUser != null) {
                throw new RuntimeException("用户" + user.getName() + "已存在");
            } else {
                user.setSalt(UUID.randomUUID().toString().substring(0, 5));
                user.setPassword(MD5Util.MD5(user.getPassword() + user.getSalt()));
                user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
                int success = userDao.insertUser(user);
                if (success > 0) {
                    return true;
                } else {
                    throw new RuntimeException("数据库操作失败，请重试！");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("新增用户失败:" + e.getMessage());
        }
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        try{
            int success = userDao.deleteUserById(id);
            if (success > 0) {
                return true;
            } else {
                throw new RuntimeException("删除用户失败！");
            }
        }catch (Exception e){
            throw new RuntimeException("删除用户失败！");
        }

    }

    @Override
    public User queryUserById(Integer id) {
        try{
            User user = userDao.queryUserById(id);
            if (user!=null){
                return user;
            }else {
                throw new RuntimeException("用户不存在！");
            }
        }catch(Exception e){
            throw new RuntimeException("获取用户信息失败");
        }

    }

    @Override
    public Map<String,Object> queryUser(String keyString, Integer pageNum, Integer pageSize) {
        Map<String,Object> result = new HashMap<>();
        try{
            List<User> users = userDao.queryUser(keyString,pageNum,pageSize);
            int total = userDao.userCount();
            result.put("total",total);
            result.put("pageNum",pageNum);
            result.put("pageSize",pageSize);
            result.put("users",users);
            return result;
        }catch(Exception e){
            logger.info(e.getMessage());
            throw new RuntimeException("获取用户信息失败");
        }
    }

    @Override
    public Boolean updateUser(User user) {
        try{
            user.setSalt(UUID.randomUUID().toString().substring(0, 5));
            user.setPassword(MD5Util.MD5(user.getPassword() + user.getSalt()));
            int success = userDao.updateUser(user);
            if (success > 0) {
                return true;
            } else {
                logger.info(String.valueOf(success));
                throw new RuntimeException("更新用户失败！");
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            throw new RuntimeException("更新用户失败！");
        }
    }
}
