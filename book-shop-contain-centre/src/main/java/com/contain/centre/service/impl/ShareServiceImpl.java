package com.contain.centre.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.applet.common.enums.AuditStatusEnum;
import com.applet.common.util.JSONResult;
import com.contain.centre.feignclient.UserCentreFeignClient;
import com.contain.centre.mapper.ShareMapper;
import com.contain.centre.model.dto.content.ShareAuditDTO;
import com.contain.centre.model.dto.content.ShareDTO;
import com.contain.centre.model.dto.user.UserDTO;
import com.contain.centre.model.entity.Share;
import com.contain.centre.service.ShareService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @author novLi
 * @date 2019年12月31日 12:53
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private UserCentreFeignClient userCentreFeignClient;

    @Autowired
    private ShareMapper shareMapper;

    @Override
    public ShareDTO findById(Integer id) {
        Share share = shareMapper.selectById(id);

        ShareDTO shareDTO = new ShareDTO();

        BeanUtils.copyProperties(share, shareDTO);
        if (ObjectUtil.isNotNull(share.getUserId())) {
            JSONResult<UserDTO> result = userCentreFeignClient.findById(share.getUserId());
            if (ObjectUtil.isNotNull(result.getData())) {
                UserDTO userDTO = result.getData();
                shareDTO.setWxNickname(userDTO.getWxNickname());
            }
        }
        return shareDTO;
    }

    @Override
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = shareMapper.selectById(id);
        if (ObjectUtil.isNull(share)) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!StrUtil.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }

        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            // 发送半消息。。
            String transactionId = UUID.randomUUID().toString();


        }

        return null;
    }
}
