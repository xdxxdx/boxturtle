package com.xdx.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_admin")
public class Admin {
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "true_name")
    private String trueName;

    @Column(name = "user_face")
    private String userFace;

    @Column(name = "brief_info")
    private String briefInfo;

    @Column(name = "role_id")
    private Integer roleId;

    private String pwd;

    @Column(name = "creat_time")
    private Date creatTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_del")
    private Integer isDel;

    /**
     * @return admin_id
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * @param adminId
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * @return admin_name
     */
    public String getAdminName() {
        return adminName;
    }

    /**
     * @param adminName
     */
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    /**
     * @return true_name
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * @param trueName
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    /**
     * @return user_face
     */
    public String getUserFace() {
        return userFace;
    }

    /**
     * @param userFace
     */
    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    /**
     * @return brief_info
     */
    public String getBriefInfo() {
        return briefInfo;
    }

    /**
     * @param briefInfo
     */
    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return creat_time
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return is_del
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * @param isDel
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}