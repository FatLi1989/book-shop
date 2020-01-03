package com.contain.centre.sentinel.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.applet.common.util.JSONResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author novLi
 * @date 2020年01月03日 11:11
 */
@Slf4j
public class ShareIdBlockHandler {

    public static JSONResult block(Integer id, BlockException e) {
        log.warn("限流，或者降级了 block", e);
        return JSONResult.errorMsg("限流");
    }
}
