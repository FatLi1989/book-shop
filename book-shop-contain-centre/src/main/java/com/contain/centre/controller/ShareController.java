package com.contain.centre.controller;

import cn.hutool.core.util.ObjectUtil;
import com.applet.common.util.JSONResult;
import com.baomidou.mybatisplus.extension.api.R;
import com.contain.centre.feignclient.SendRequestByUrlFeignClient;
import com.contain.centre.model.dto.share.ShareDTO;
import com.contain.centre.service.ShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "分享信息", tags = {"分享信息"})
@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private SendRequestByUrlFeignClient sendRequestByUrlFeignClient;

    @ApiOperation(value = "根据id查询分享详情", notes = "根据id查询分享详情")
    @GetMapping("/{id}")
    public JSONResult findById(@PathVariable Integer id) {
        if (ObjectUtil.isNull(id)) {
            return JSONResult.errorMsg("分享id不可以为空");
        }
        return JSONResult.ok(shareService.findById(id));
    }

    @ApiOperation(value = "查询百度", notes = "查询百度")
    @GetMapping("/baidu")
    public String skipToBaidu() {
       return sendRequestByUrlFeignClient.index();
    }






}
