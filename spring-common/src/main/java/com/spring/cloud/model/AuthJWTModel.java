package com.spring.cloud.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: jwt使用的auth对象model
 * @author: Lyle
 * @create: 2019-10-05 17:34
 **/
public class AuthJWTModel implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
     * 用户登录账号
     */
	private String account;
    /**
     * 用户登录id
     */
    private Integer userId;
    /**
     * 类型 0 app 1web 2 api
     */
    private String equipment;
    /**
     * 登录设备
     */
    private String device;
    /**
     * 登录时间
     */
    private String loginDate;

    private String token;
    /**
     * 0退出登录 1上线中
     */
    private Integer status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
