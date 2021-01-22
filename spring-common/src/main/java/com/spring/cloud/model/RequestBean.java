package com.spring.cloud.model;

import lombok.Data;

@Data
public class RequestBean {

    private String accesskey;

    private String secret;

    private String sign;

    private String templateId;

    private String mobile;

    private String content;

    private Integer code;
}
