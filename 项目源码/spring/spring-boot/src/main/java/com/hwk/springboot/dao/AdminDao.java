package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

@Repository
public interface AdminDao extends Mapper<Admin> {
//    @Select("select * from admin_m")
    List<Admin> getUser();

    List<Admin> getBySearch(@Param("params") Params params);
    void addAdmin(@Param("admin") Admin admin);

    void updateAdmin(@Param("admin") Admin admin);

    void deleteAdmin(Integer id);

    Admin getBySearchUsername(@Param("username") String username);

    Admin getBySearchUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    Admin getBySearchId(@Param("id") Integer adminId);

    List<Admin> getUnAccept(@Param("params") Params params);

    List<Admin> getBySearchIdList(@Param("params") Params params);

    List<Map<Long, String>> getByRole();
}
