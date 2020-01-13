package com.user.centre.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.ObjectUtil;
import com.applet.common.util.JSONResult;
import com.user.centre.model.dto.user.LoginRespDTO;
import com.user.centre.model.dto.user.UserLoginDTO;
import com.user.centre.serivce.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author novLi
 * @date 2019年12月31日 10:46
 */

@Api(value = "用户信息", tags = {"查询用户信息"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final WxMaService wxMaService;

    @ApiOperation(value = "用户是否存在", notes = "用户是否存在")
    @GetMapping("/{id}")
    public JSONResult findById(@PathVariable Integer id) {
        if (ObjectUtil.isNull(id)) {
            return JSONResult.errorMsg("用户标识不可以为空");
        }
        return JSONResult.ok(userService.findById(id));
    }

   /* @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                .getSessionInfo(loginDTO.getCode());

        // 微信的openId，用户在微信这边的唯一标示
        String openid = result.getOpenid();
    }*/

}
