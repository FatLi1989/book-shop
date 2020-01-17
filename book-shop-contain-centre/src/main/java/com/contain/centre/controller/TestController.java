package com.contain.centre.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author novLi
 * @date 2020年01月17日 14:36
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("test")
public class TestController {

    @Value("${test}")
    private String value;

    @GetMapping("test")
    public void test() {
        log.info(value);
    }

}
