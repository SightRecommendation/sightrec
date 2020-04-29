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
    @Autowired
    UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        try {
            int success = userDao.deleteUserById(id);
            if (success > 0) {
                return true;
            } else {
                throw new RuntimeException("删除用户失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除用户失败！");
        }

    }

    @Override
    public User queryUserById(Integer id) {
        try {
            User user = userDao.queryUserById(id);
            if (user != null) {
                return user;
            } else {
                throw new RuntimeException("用户不存在！");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }

    @Override
    public User queryUserByName(String name) {
        try {
            User user = userDao.queryUserByName(name);
            if (user != null) {
                return user;
            } else {
                throw new RuntimeException("用户不存在！");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }

    @Override
    public Map<String, Object> queryUser(String keyString, Integer pageNum, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<User> users = userDao.queryUser(keyString, pageNum, pageSize);
            int total = userDao.userCount();
            result.put("total", total);
            result.put("pageNum", pageNum);
            result.put("pageSize", pageSize);
            result.put("users", users);
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new RuntimeException("获取用户信息失败");
        }
    }

    @Override
    public Boolean updateUser(User user) {
        try {
            User foundUser = userDao.queryUserByName(user.getName());
            if (foundUser == null || foundUser.getId().equals(user.getId())) {
                user.setName(user.getName());
                user.setSalt(UUID.randomUUID().toString().substring(0, 5));
                user.setPassword(MD5Util.MD5(user.getPassword() + user.getSalt()));
                user.setHeadUrl(user.getHeadUrl());
                user.setPhone(user.getPhone());
                user.setEmail(user.getEmail());
                int success = userDao.updateUser(user);
                if (success > 0) {
                    return true;
                } else {
                    logger.info("成功码：" + success);
                    throw new RuntimeException("更新用户失败了！");
                }
            } else {
                throw new RuntimeException("用户名已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败：" + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> login(User user) {
        try {
            User foundUser = userDao.queryUserByName(user.getName());
            if (foundUser == null) {
                logger.info("用户不存在");
                throw new RuntimeException("用户" + user.getName() + "不存在");
            } else if (!foundUser.getPassword().equals(MD5Util.MD5(user.getPassword() + foundUser.getSalt()))) {
                logger.info(foundUser.getPassword());
                logger.info(MD5Util.MD5(user.getPassword() + foundUser.getSalt()));
                logger.info("密码错误");
                throw new RuntimeException("密码错误");
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("id", foundUser.getId());
                data.put("name", foundUser.getName());
                data.put("token", foundUser.getPassword());
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("登录失败：" + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> register(User user) {
        try {
            User foundUser = userDao.queryUserByName(user.getName());
            //logger.info(foundUser.getName());
            if (foundUser == null) {
                insertUser(user);
                User newUser = userDao.queryUserByName(user.getName());
                Map<String, Object> data = new HashMap<>();
                data.put("id", newUser.getId());
                data.put("name", newUser.getName());
                data.put("token", newUser.getPassword());
                return data;
            } else {
                throw new RuntimeException("用户名已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("注册失败：" + e.getMessage());
        }
    }
}
