package com.wlc.sightrec.controller;

import com.alibaba.fastjson.JSONObject;
import com.wlc.sightrec.entity.Sight;
import com.wlc.sightrec.service.SightService;
import com.wlc.sightrec.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class SightController {

    @Autowired
    SightService sightService;

    @RequestMapping(path = {"/sights"}, method = {RequestMethod.GET})
    public JSONObject getComments(@RequestParam("query") String query,
                                  @RequestParam("pageNum") int pageNum,
                                  @RequestParam("pageSize") int pageSize,
                                  HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        // query 为搜索参数
        String auth = request.getHeader("Authorization");

        List<Sight> sightList;
        List<Sight> sightListByPage;
        JSONObject getSights = new JSONObject();
        try {
            // 模糊搜索，返回所有符合条件的景点
            query = "%" + query + "%";
            sightList = sightService.getSightsByName(query);
            // 根据 页码 和 每页景点数 分页
            int sightNum = sightList.size();
            int totalPage = (int) Math.ceil(sightNum * 1.0 / pageSize);
            int offset = pageSize * (pageNum - 1);
            sightListByPage = sightList.subList(offset, Math.min(sightNum, offset + pageSize));
            getSights.put("data", JsonUtil.getSightData(totalPage, pageNum, sightNum, sightListByPage));
            getSights.put("meta", JsonUtil.getMeta("获取景点列表成功", 200));
            return getSights;
        } catch (Exception e) {
            getSights.put("data", null);
            getSights.put("meta", JsonUtil.getMeta("获取景点列表失败", 400));
            return getSights;
        }
    }

    @RequestMapping(path = {"/sights/{id}"}, method = {RequestMethod.GET})
    public JSONObject getComment(@PathVariable("id") int id,
                                 HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject getSight = new JSONObject();
        try {
            Sight sightById = sightService.getSightById(id);
            getSight.put("data", sightById);
            getSight.put("meta", JsonUtil.getMeta("获取评论成功", 200));
            return getSight;
        } catch (Exception e) {
            getSight.put("data", null);
            getSight.put("meta", JsonUtil.getMeta("获取景点列表失败", 400));
            return getSight;
        }
    }

    @RequestMapping(path = {"/sights/{id}"}, method = {RequestMethod.PUT})
    public JSONObject modifyComment(@PathVariable("id") int id, @RequestBody Sight changedSight,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject modifySight = new JSONObject();
        try {
            sightService.modifySight(changedSight);
            modifySight.put("data", null);
            modifySight.put("meta", JsonUtil.getMeta("修改评论成功", 200));
            return modifySight;
        } catch (Exception e) {
            modifySight.put("data", null);
            modifySight.put("meta", JsonUtil.getMeta("修改评论失败", 400));
            return modifySight;
        }
    }

    @RequestMapping(path = {"/sights/{id}"}, method = {RequestMethod.DELETE})
    public JSONObject deleteComment(@PathVariable("id") int id,
                                    HttpServletRequest request, HttpServletResponse response) {
        // token 验证没写
        String auth = request.getHeader("Authorization");

        JSONObject deleteSight = new JSONObject();
        try {
            sightService.deleteSight(id);
            deleteSight.put("data", null);
            deleteSight.put("meta", JsonUtil.getMeta("删除景点成功", 200));
            return deleteSight;
        } catch (Exception e) {
            deleteSight.put("data", null);
            deleteSight.put("meta", JsonUtil.getMeta("删除景点失败", 400));
            return deleteSight;
        }
    }
}
