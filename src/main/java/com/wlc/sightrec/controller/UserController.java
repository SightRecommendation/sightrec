package com.wlc.sightrec.controller;

import com.wlc.sightrec.entity.BaseResponse;
import com.wlc.sightrec.entity.User;
import com.wlc.sightrec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = {"/users", ""}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse insertUser(@RequestBody User user) {
        try {
            userService.insertUser(user);
            return BaseResponse.ok("新增用户成功", null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/users", ""}, method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    public BaseResponse deleteUser(@RequestParam(value = "id") Integer id) {
        try {
            userService.deleteUserById(id);
            return BaseResponse.ok("删除用户成功", null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/users", ""}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public BaseResponse queryUserById(@RequestParam(value = "id") Integer id) {
        try {
            User user = userService.queryUserById(id);
            return BaseResponse.ok("查询成功", user);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/users/name", ""}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public BaseResponse queryUserByName(@RequestParam(value = "name") String name) {
        try {
            User user = userService.queryUserByName(name);
            return BaseResponse.ok("查询成功", user);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/users", ""}, method = RequestMethod.GET, produces = "application/json;charset=utf-8", params = {"keyString", "pageNum", "pageSize"})
    public BaseResponse queryUser(@RequestParam(value = "keyString") String keyString,
                                  @RequestParam(value = "pageNum") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize) {
        try {
            Map<String, Object> data = userService.queryUser(keyString, pageNum, pageSize);
            return BaseResponse.ok("查询成功", data);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/users", ""}, method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public BaseResponse updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return BaseResponse.ok("修改成功", null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"users/login"}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse login(@RequestBody User user) {
        try {
            Object data = userService.login(user);
            return BaseResponse.ok("登录成功", data);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"users/register"}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse register(@RequestBody User user) {
        try {
            Object data = userService.register(user);
            return BaseResponse.ok("登录成功", data);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }
}
