package com.spring.cloud.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserInfo
 * @Description 系统用户信息取值
 * @Author sunshaoxian
 * @Date 2019/11/27
 * @Version 1.0
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String phone;

    /**
     * 邀请码
     */
    private String shareCode;

    /**
     * 拼多多外链
     */
    private String pddPid;
    /**
     * 淘宝外链
     */
    private String taobaoPid;
    /**
     * 京东外链
     */
    private String jdPid;

    /**
     * 邀请人
     */
    private Integer invitees;

}
