package com.spring.cloud.util.token;


import java.io.Serializable;
import java.util.Date;


public class Auth implements Serializable {
    private static final long serialVersionUID = 123452343265465L;

    /**
     * 用户登录账号
     */
    private String account;

    /**
     * 用户登录id
     */
    private Integer userId;


    /**
     * 创建时间
     */
    private Date createDate;


    public Auth() {
    }

    public Auth(String account, Integer userId, Date createDate) {
        this.account = account;
        this.userId = userId;
        this.createDate = createDate;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
