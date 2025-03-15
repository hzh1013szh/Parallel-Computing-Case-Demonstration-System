package com.hwk.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.service.AdminService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Resource
    private AdminService adminService;
    @GetMapping
    public Result getAll(){
        List<Admin> list = adminService.getAll();
        return Result.success(list);
    }
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        admin.setStatus("0");
        adminService.addAdmin(admin);
        return Result.success();
    }
    @GetMapping("/search")
    public Result getBySearch(Params params){
        PageInfo<Admin> info = adminService.getBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/search/{id}")
    public Result GetById(@PathVariable Integer id){
//        log.info("getById:{}",id);
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }
    @PostMapping("/search/idlist")
    public Result getAdminByIdList(@RequestBody Params params){
        PageInfo<Admin> info = adminService.getAdminByIdList(params);
        return Result.success(info);
    }

    @PostMapping
    public Result saveAdmin(@RequestBody Admin admin){
        log.info(admin.getStatus());
        if(admin.getId() == null){
            adminService.addAdmin(admin);
        }
        else{
            adminService.updateAdmin(admin);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        Admin loginadmin = adminService.login(admin);
        return Result.success(loginadmin);
    }

    @GetMapping("/search/UnAccept")
    public Result getUnAccept(Params params){
        PageInfo<Admin> info = adminService.getUnAccept(params);
        return Result.success(info);
    }
    @GetMapping("/echarts/role_pie")
    public Result getRolePie(){
        List<Map<Long, String>> list = adminService.getByRole();
        return Result.success(list);
    }
}
