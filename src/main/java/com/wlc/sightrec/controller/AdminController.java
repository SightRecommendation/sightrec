package com.wlc.sightrec.controller;

import com.wlc.sightrec.entity.Admin;
import com.wlc.sightrec.entity.User;
import com.wlc.sightrec.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Admin admin) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, Object> data = adminService.login(admin);
            map.put("data", data);
            map.put("msg", "管理员登录成功");
            return new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> insertAdmin(@RequestBody Admin admin) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            adminService.insertAdmin(admin);
            map.put("msg", "新增管理员成功");
            return new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }
    }
}
