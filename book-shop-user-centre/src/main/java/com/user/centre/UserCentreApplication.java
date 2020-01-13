package com.user.centre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan(basePackages = {"com.user.centre.mapper"})
@SpringBootApplication(scanBasePackages = {"com.user.centre", "com.applet.common"})
public class UserCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCentreApplication.class, args);
    }

}
