package com.contain.centre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(Source.class)
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.contain.centre.mapper"})
@SpringBootApplication(scanBasePackages = {"com.contain.centre"})
public class ContainCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainCentreApplication.class, args);
    }
}
