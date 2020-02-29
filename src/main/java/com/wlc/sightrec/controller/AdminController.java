package com.wlc.sightrec.controller;

import com.wlc.sightrec.entity.Admin;
import com.wlc.sightrec.entity.BaseResponse;
import com.wlc.sightrec.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse login(@RequestBody Admin admin) {
        try {
            Object data = adminService.login(admin);
            return BaseResponse.ok("管理员登录成功", data);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse insertAdmin(@RequestBody Admin admin) {
        try {
            adminService.insertAdmin(admin);
            return BaseResponse.ok("新增管理员成功", null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }
}
