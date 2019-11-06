package com.xdx.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.xdx.common.dto.BaseResponse;
import com.xdx.common.dto.PageResponse;
import com.xdx.model.Admin;
import com.xdx.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @RequestMapping("adminList")
    public BaseResponse<List<Admin>> getAdminList(@RequestParam(value = "pageSize",required = false) Integer pageSize,
                                                  @RequestParam(value = "pageNum",required = false) Integer pageNum){
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Admin> result = adminService.selectListAll();
        return PageResponse.successPage(result);
    }
}
