package com.user.centre.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.ObjectUtil;
import com.applet.common.auth.CheckLogin;
import com.applet.common.util.JSONResult;
import com.applet.common.util.JwtOperator;
import com.user.centre.model.dto.user.JwtTokenRespDTO;
import com.user.centre.model.dto.user.LoginRespDTO;
import com.user.centre.model.dto.user.UserLoginDTO;
import com.user.centre.model.dto.user.UserRespDTO;
import com.user.centre.model.entity.User;
import com.user.centre.serivce.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novLi
 * @date 2019年12月31日 10:46
 */

@Slf4j
@Api(value = "用户信息", tags = {"查询用户信息"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final WxMaService wxMaService;

    private final JwtOperator jwtOperator;

    @CheckLogin
    @ApiOperation(value = "用户是否存在", notes = "用户是否存在")
    @GetMapping("/{id}")
    public JSONResult findById(@PathVariable Integer id) {
        if (ObjectUtil.isNull(id)) {
            return JSONResult.errorMsg("用户标识不可以为空");
        }
        return JSONResult.ok(userService.findById(id));
    }


    @ApiOperation(value = "生成token", notes = "生成token")
    @GetMapping("/genToken")
    public String genToken() {
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", 1);
        userInfo.put("wxNickname", "你爸爸");
        userInfo.put("role", "admin");
        return this.jwtOperator.generateToken(userInfo);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        //微信小程序服务端校验是否已经登录的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                .getSessionInfo(loginDTO.getCode());
        // 微信的openId，用户在微信这边的唯一标示
        String openid = result.getOpenid();
        // 看用户是否注册，如果没有注册就（插入）
        // 如果已经注册
        User user = this.userService.login(loginDTO, openid);

        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);

        log.info("用户 {} 登录成功, 生成的token = {} , 有效期到: {}",
                loginDTO.getWxNickname(),
                token,
                jwtOperator.getExpirationTime()
        );

        return LoginRespDTO.builder()
                .user(UserRespDTO.builder()
                        .id(user.getId())
                        .avatarUrl(user.getAvatarUrl())
                        .bonus(user.getBonus())
                        .wxNickname(user.getWxNickname())
                        .build())
                .token(JwtTokenRespDTO.builder()
                        .expirationTime(jwtOperator.getExpirationTime().getTime())
                        .token(token)
                        .build())
                .build();

    }

}
