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
    public ResponseEntity<Map<String,Object>> index(@RequestBody User user){
        Map<String,Object> map = new HashMap<String,Object>();
        String msg = "";
        try {
            userService.insertUser(user);
            map.put("msg","新增用户成功");
            return new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }
}
