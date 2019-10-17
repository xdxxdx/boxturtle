package com.xdx.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.xdx.model.Admin;
import com.xdx.service.AdminService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminController {
    @Resource(name="adminServiceImpl")
    private AdminService adminService;
    @RequestMapping("adminDetail")
    public String adminDetail(int adminId){
        Admin admin=adminService.selectById(adminId);
        return admin.getAdminName();
    }
    @CrossOrigin
    @RequestMapping("adminSave")
    public Integer adminSave(Admin admin){
        Integer result=adminService.insertSelective(admin);
        return result;

    }
}
