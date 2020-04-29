package com.wlc.sightrec.service.impl;

import com.wlc.sightrec.dao.AdminDao;
import com.wlc.sightrec.entity.Admin;
import com.wlc.sightrec.service.AdminService;
import com.wlc.sightrec.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public boolean insertAdmin(Admin admin) {
        try {
            Admin foundAdmin = adminDao.queryAdminByName(admin.getName());
            if (foundAdmin != null) {
                throw new RuntimeException("管理员" + admin.getName() + "已存在");
            } else {
                admin.setSalt(UUID.randomUUID().toString().substring(0, 5));
                admin.setPassword(MD5Util.MD5(admin.getPassword() + admin.getSalt()));
                int success = adminDao.insertAdmin(admin);
                if (success > 0) {
                    return true;
                } else {
                    throw new RuntimeException("数据库操作失败，请重试！");
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new RuntimeException("新增管理员失败:" + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> login(Admin admin) {
        try {
            Admin foundAdmin = adminDao.queryAdminByName(admin.getName());
            if (foundAdmin == null) {
                throw new RuntimeException("管理员" + admin.getName() + "不存在");
            } else if (!foundAdmin.getPassword().equals(MD5Util.MD5(admin.getPassword() + foundAdmin.getSalt()))) {
                throw new RuntimeException("密码错误");
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("name", foundAdmin.getName());
                data.put("token", "ruok");
                return data;
            }
        } catch (Exception e) {
            throw new RuntimeException("登录失败：" + e.getMessage());
        }
    }
}
