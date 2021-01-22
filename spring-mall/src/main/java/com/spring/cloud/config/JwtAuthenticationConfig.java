package com.spring.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Config JWT
 * @author yuanc
 */
@Configuration
@Data
public class JwtAuthenticationConfig {

    private String url;

    private String header;

    private String clientId;

    private String prefix;

    private int expiration; // default 1 年

    private String secret;

}
