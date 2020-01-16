package com.contain.centre.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.applet.common.auth.CheckAuthorization;
import com.applet.common.util.JSONResult;
import com.contain.centre.feignclient.SendRequestByUrlFeignClient;
import com.contain.centre.service.ShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "分享信息", tags = {"分享信息"})
@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {

    private final ShareService shareService;

    private final SendRequestByUrlFeignClient sendRequestByUrlFeignClient;

    @ApiOperation(value = "根据id查询分享详情", notes = "根据id查询分享详情")
    @GetMapping("/{id}")
    @CheckAuthorization("admin")
    @SentinelResource(value = "query-content-byId", blockHandler = "block", fallback = "fallback")
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

    public static JSONResult block(Integer id, BlockException e) {
        return JSONResult.errorMsg("限流了");
    }

    public static JSONResult fallback(Integer id) {
        return JSONResult.errorMsg("降级了");
    }
}
