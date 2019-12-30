package com.user.centre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.applet")
@SpringBootApplication
public class UserCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCentreApplication.class, args);
    }

}
