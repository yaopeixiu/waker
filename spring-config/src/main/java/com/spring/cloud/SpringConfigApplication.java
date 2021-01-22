package com.spring.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.TimeZone;

@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringConfigApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		new SpringApplicationBuilder(SpringConfigApplication.class).web(true).run(args);
	}
}
