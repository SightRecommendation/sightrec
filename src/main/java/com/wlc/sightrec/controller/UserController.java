package com.wlc.sightrec.controller;

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

    @RequestMapping(value = {"/",""},method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public ResponseEntity<Map<String,Object>> insertUser(@RequestBody User user){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            userService.insertUser(user);
            map.put("msg","新增用户成功");
            return new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = {"/",""},method= RequestMethod.DELETE,produces="application/json;charset=utf-8")
    public ResponseEntity<Map<String,Object>> deleteUser(@RequestParam(value="id") Integer id){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            userService.deleteUserById(id);
            map.put("msg","删除用户成功");
            return new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = {"/",""},method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public ResponseEntity<Map<String,Object>> queryUserById(@RequestParam(value="id") Integer id){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            User user = userService.queryUserById(id);
            map.put("data",user);
            return new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }
    @RequestMapping(value = {"/",""},method= RequestMethod.GET,produces="application/json;charset=utf-8",params = {"keyString","pageNum","pageSize"})
    public ResponseEntity<Map<String,Object>> queryUser(@RequestParam(value="keyString") String keyString,
                                                        @RequestParam(value="pageNum") Integer pageNum,
                                                        @RequestParam(value="pageSize") Integer pageSize){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Map<String,Object> data = userService.queryUser(keyString,pageNum,pageSize);
            map.put("data",data);
            return new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = {"/",""},method= RequestMethod.PUT,produces="application/json;charset=utf-8")
    public ResponseEntity<Map<String,Object>> updateUser(@RequestBody User user){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            userService.updateUser(user);
            map.put("msg","修改成功");
            return new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }
}
