package com.contain.centre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan(basePackages = {"com.contain.centre.mapper"})
@SpringBootApplication(scanBasePackages = {"com.contain.centre"})
public class ContainCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainCentreApplication.class, args);
    }
}
