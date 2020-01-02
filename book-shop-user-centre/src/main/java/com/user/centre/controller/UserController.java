package com.user.centre.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.applet.common.util.JSONResult;
import com.user.centre.model.entity.User;
import com.user.centre.serivce.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author novLi
 * @date 2019年12月31日 10:46
 */

@Api(value = "用户信息", tags = {"查询用户信息"})
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户是否存在", notes = "用户是否存在")
    @GetMapping("/{id}")
    public JSONResult findById(@PathVariable Integer id) {
        if (ObjectUtil.isNull(id)) {
            return JSONResult.errorMsg("用户标识不可以为空");
        }
        return JSONResult.ok(userService.findById(id));
    }


}
