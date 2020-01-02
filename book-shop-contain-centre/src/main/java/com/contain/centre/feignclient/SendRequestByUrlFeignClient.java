package com.contain.centre.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 脱离robbin 调用外部url
 **/
@FeignClient(name = "sendRequest", url = "www.baidu.com")
public interface SendRequestByUrlFeignClient {

    @GetMapping("")
    String index();



}
