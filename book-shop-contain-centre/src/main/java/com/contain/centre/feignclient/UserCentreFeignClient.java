package com.contain.centre.feignclient;

import com.applet.common.util.JSONResult;
import com.contain.centre.feignclient.fallbackfactory.UserCentreFeignClientFallbackFactory;
import com.contain.centre.model.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-centre", fallbackFactory = UserCentreFeignClientFallbackFactory.class)
public interface UserCentreFeignClient {
    /**
     * http://user-center/users/{id}
     */
    @GetMapping("/users/{id}")
    JSONResult<UserDTO> findById(@PathVariable Integer id);

}
