package com.spring.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@EnableCaching
@EnableAsync
@EnableFeignClients
@EnableEurekaClient
@MapperScan(basePackages = {"com.spring.cloud.dao"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringUserApplication {

    static Logger logger = LoggerFactory.getLogger(SpringUserApplication.class);

    public static void main(String[] args) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
            ConfigurableApplicationContext run = new SpringApplicationBuilder(SpringUserApplication.class).web(true).run(args);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
