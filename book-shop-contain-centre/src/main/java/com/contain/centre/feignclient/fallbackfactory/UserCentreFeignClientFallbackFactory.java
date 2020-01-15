package com.contain.centre.feignclient.fallbackfactory;

import com.applet.common.util.JSONResult;
import com.contain.centre.feignclient.UserCentreFeignClient;
import com.contain.centre.model.dto.user.UserDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author novLi
 * @date 2020年01月03日 16:53
 */
@Slf4j
@Component
public class UserCentreFeignClientFallbackFactory implements FallbackFactory<UserCentreFeignClient> {
    @Override
    public UserCentreFeignClient create(Throwable throwable) {
        return new UserCentreFeignClient() {
            @Override
            public JSONResult<UserDTO> findById(Integer id, String token) {
                UserDTO userDTO = new UserDTO();
                userDTO.setWxNickname("nickName");
                return JSONResult.ok(userDTO);
            }
        };
    }
}
