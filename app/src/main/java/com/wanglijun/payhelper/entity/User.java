package com.wanglijun.payhelper.entity;

public class User {


    /**
     * id : 2
     * app_user : 22610213
     * app_pass : b9c67dcd5c22feecfd51628b7c849ba4
     * last_login_ip : 117.60.71.153
     * last_login_time : 1555407971
     * spay_acccount : 5235215312
     * status : 0
     * c_id : 2
     * userId : 22
     * type : 3
     * use_count : 18
     * pay_url : /upload/20190416/c2ba7d55f9b66d399d0da444690d4c87.jpeg
     * createdTime : 1555293116
     * updatedTime : 1555407051
     * sts : 0
     */

    private int id;
    private String app_user;
    private String app_pass;
    private String last_login_ip;
    private int last_login_time;
    private String spay_acccount;
    private int status;
    private int c_id;
    private int userId;
    private int type;
    private int use_count;
    private String pay_url;
    private int createdTime;
    private int updatedTime;
    private int sts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_user() {
        return app_user;
    }

    public void setApp_user(String app_user) {
        this.app_user = app_user;
    }

    public String getApp_pass() {
        return app_pass;
    }

    public void setApp_pass(String app_pass) {
        this.app_pass = app_pass;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public int getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(int last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getSpay_acccount() {
        return spay_acccount;
    }

    public void setSpay_acccount(String spay_acccount) {
        this.spay_acccount = spay_acccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUse_count() {
        return use_count;
    }

    public void setUse_count(int use_count) {
        this.use_count = use_count;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public int getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(int updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getSts() {
        return sts;
    }

    public void setSts(int sts) {
        this.sts = sts;
    }
}
