package com.wlc.sightrec.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BaseResponse {
    private Object data;
    private Object meta;

    public static BaseResponse ok(String msg, Object data) {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> meta = new HashMap<>();
        meta.put("status", 200);
        meta.put("msg", msg);
        baseResponse.setData(data);
        baseResponse.setMeta(meta);
        return baseResponse;
    }

    public static BaseResponse fail(String msg, Object data) {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> meta = new HashMap<>();
        meta.put("status", 403);
        meta.put("msg", msg);
        baseResponse.setData(data);
        baseResponse.setMeta(meta);
        return baseResponse;
    }
}
