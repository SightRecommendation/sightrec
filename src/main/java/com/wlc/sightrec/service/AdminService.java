package com.wlc.sightrec.service;

import com.wlc.sightrec.entity.Admin;
import com.wlc.sightrec.entity.User;

import java.util.Map;

public interface AdminService {
    boolean insertAdmin(Admin admin);
    Map<String, Object> login(Admin admin);

}
