package com.contain.centre.feignclient;

import com.contain.centre.model.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-centre")
public interface UserCentreFeignClient {
    /**
     * http://user-center/users/{id}
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);

}
