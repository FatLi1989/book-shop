package com.contain.centre.controller;

import com.applet.common.util.JSONResult;
import com.contain.centre.model.dto.content.ShareAuditDTO;
import com.contain.centre.service.ShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author novLi
 * @date 2020年01月04日 14:18
 */
@Api(value = "发布审核", tags = {"发布审核"})
@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Autowired
    ShareService shareService;


    @ApiOperation(value = "根据id查询分享详情", notes = "根据id查询分享详情")
    @PutMapping("/audit/{id}")
    public JSONResult auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO) {
        this.shareService.auditById(id, auditDTO);
        return JSONResult.ok("结果");
    }

}
