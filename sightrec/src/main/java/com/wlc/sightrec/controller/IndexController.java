package com.wlc.sightrec.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "hello world";
    }

    @RequestMapping(path = {"/menus"}, method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject getMenus(HttpServletRequest request, HttpServletResponse response) {
        // token 验证
        String auth = request.getHeader("Authorization");

        JSONObject menuList = new JSONObject();
        try {
            JSONArray dataJson = new JSONArray();
            String userJson = "{\"authName\":\"用户管理\",\"id\":\"1\",\"path\":\"users\"}";
            String sightJson = "{\"authName\":\"景点管理\",\"id\":\"2\",\"path\":\"sights\"}";
            String commentJson = "{\"authName\":\"评论管理\",\"id\":\"3\",\"path\":\"comments\"}";
            dataJson.add(JSONObject.parse(userJson));
            dataJson.add(JSONObject.parse(sightJson));
            dataJson.add(JSONObject.parse(commentJson));
            menuList.put("data", dataJson);
            menuList.put("meta", JsonUtil.getMeta("获取菜单列表成功", 200));
        } catch (Exception e) {
            menuList.put("data", null);
            menuList.put("meta", JsonUtil.getMeta("获取菜单列表失败", 400));
        }
        return menuList;
    }
}
