package com.wlc.sightrec.dao;

import com.wlc.sightrec.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminDao {
    int insertAdmin(Admin admin);
    Admin queryAdminByName(String name);
}
