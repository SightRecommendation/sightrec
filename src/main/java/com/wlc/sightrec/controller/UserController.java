package com.wlc.sightrec.controller;

import com.wlc.sightrec.entity.BaseResponse;
import com.wlc.sightrec.entity.User;
import com.wlc.sightrec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse insertUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userService.insertUser(user);
            return BaseResponse.ok("新增用户成功", null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    public BaseResponse deleteUser(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userService.deleteUserById(id);
            return BaseResponse.ok("删除用户成功", null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public BaseResponse queryUserById(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.queryUserById(id);
            return BaseResponse.ok("查询成功", user);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(), null);
        }
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET, produces = "application/json;charset=utf-8", params = {"keyString", "pageNum", "pageSize"})
    public BaseResponse queryUser(@RequestParam(value = "keyString") String keyString,
                                                         @RequestParam(value = "pageNum") Integer pageNum,
                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        try {
            Map<String, Object> data = userService.queryUser(keyString, pageNum, pageSize);
            return BaseResponse.ok("查询成功",data);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(),null);
        }
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public BaseResponse updateUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userService.updateUser(user);
            return BaseResponse.ok("修改成功",null);
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage(),null);
        }
    }
}
