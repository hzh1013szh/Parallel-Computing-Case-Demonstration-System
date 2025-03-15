package com.hwk.springboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwk.springboot.common.JwtTokenUtils;
import com.hwk.springboot.dao.AdminDao;
import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Admin> getAll() {
        return adminDao.getUser();
    }


    public PageInfo<Admin> getBySearch(Params params) {
        // 分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        // 接下来的查询会自动分页
        List<Admin> list = adminDao.getBySearch(params);
        return PageInfo.of(list);
    }

    public void addAdmin(Admin admin) {
        // 进行非空判断
        if(admin.getUsername() == null || admin.getUsername().equals("")){
            throw new CustomException("用户名不能为空");
        }
        // 加入之前我们要进行重复性判断，用户名不能重复
        Admin new_admin = adminDao.getBySearchUsername(admin.getUsername());
        if(new_admin != null){
            // 说明我们新增的用户名已经存在 不能再添加
            // 此时我们需要给前端返回一个错误信息
            throw new CustomException("该用户名已经存在，请勿重复添加");
        }
        // 初始化一个密码
        if(admin.getPassword() == null || admin.getPassword().equals("")){
            admin.setPassword("1234");
        }
        if(admin.getStatus() == null) admin.setStatus("1");
        if(admin.getMyfollow() == null) admin.setMyfollow(0);
        if(admin.getMyfan() == null) admin.setMyfan(0);
        adminDao.addAdmin(admin);
    }

    public void updateAdmin(Admin admin) {
        Admin new_admin = adminDao.getBySearchUsername(admin.getUsername());
        if(new_admin != null && !new_admin.getId().equals(admin.getId())){
            throw new CustomException("该用户名已经存在，不允许修改");
        }
        adminDao.updateAdmin(admin);
    }

    public void deleteAdmin(Integer id) {
        adminDao.deleteAdmin(id);
    }

    public Admin login(Admin admin) {
//        System.out.println("admin:"+admin.getUserid() + " " + admin.getPassword());
        // 非空判断
        if(admin.getUsername() == null || admin.getUsername().equals("")){
            throw new CustomException("用户名不能为空");
        }
        if(admin.getPassword() == null || admin.getPassword().equals("")){
            throw new CustomException("密码不能为空");
        }
        // 根据用户名和密码进行sql查询
        Admin new_admin = adminDao.getBySearchUsernameAndPassword(admin.getUsername(),admin.getPassword());
        if(new_admin == null){
            throw new CustomException("用户名或密码错误");
        }
        if(new_admin.getStatus().equals("-1")){
            throw new CustomException("该用户注册审核未通过，请联系管理员");
        }
        // 判断管理员是否审核完成
        if(new_admin.getStatus().equals("0")){
            throw new CustomException("管理员尚未审核通过该用户，请耐心等待");
        }
        String token = JwtTokenUtils.genToken(String.valueOf(new_admin.getId()),new_admin.getPassword());
        new_admin.setToken(token);
        return new_admin;
    }

    public Admin getById(Integer adminId) {
        return adminDao.getBySearchId(adminId);
    }

    public PageInfo<Admin> getUnAccept(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        // 接下来的查询会自动分页
        List<Admin> list = adminDao.getUnAccept(params);
        for(Admin admin:list){
            if(admin.getStatus().equals("-1")) {
                admin.setStatus("未通过");
            }
            else if(admin.getStatus().equals("0")) {
                admin.setStatus("审核中");
            }
        }
        return PageInfo.of(list);
    }

    public Admin getByUsername(String username) {
        return adminDao.getBySearchUsername(username);
    }

    public PageInfo<Admin> getAdminByIdList(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Admin> list = adminDao.getBySearchIdList(params);
        return PageInfo.of(list);
    }

    public List<Map<Long, String>> getByRole() {
        return adminDao.getByRole();
    }
}
