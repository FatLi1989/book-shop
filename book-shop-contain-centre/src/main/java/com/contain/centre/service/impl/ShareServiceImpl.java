package com.contain.centre.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.applet.common.enums.AuditStatusEnum;
import com.applet.common.util.JSONResult;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.contain.centre.feignclient.UserCentreFeignClient;
import com.contain.centre.mapper.RocketMqTransactionLogMapper;
import com.contain.centre.mapper.ShareMapper;
import com.contain.centre.model.dto.content.ShareAuditDTO;
import com.contain.centre.model.dto.content.ShareDTO;
import com.contain.centre.model.dto.messaging.UserAddBonusMsgDTO;
import com.contain.centre.model.dto.user.UserDTO;
import com.contain.centre.model.entity.RocketMqTransactionLog;
import com.contain.centre.model.entity.Share;
import com.contain.centre.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author novLi
 * @date 2019年12月31日 12:53
 */
@Slf4j
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private UserCentreFeignClient userCentreFeignClient;

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private RocketMqTransactionLogMapper rocketmqTransactionLogMapper;

    /**
     * 使用id查询分享内容
     *
     * @author Liyanpeng
     * @date 2020/1/9 15:57
     **/
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
    /**
     * 审批分享内容
     *
     * @author Liyanpeng
     * @date 2020/1/9 15:57
     **/
    @Override
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = shareMapper.selectById(id);
        if (ObjectUtil.isNull(share)) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!StrUtil.equals(AuditStatusEnum.NOT_YET.getMessage(), share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }
        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            log.info("---------------添加用户积分消息发送中---------------");
            // 发送正常消息
            /*sendNormalMessage(share);*/
            //发送事务消息
            sendTransactionMessage(share, auditDTO);
        } else {
            this.auditByIdInDB(id, auditDTO);
        }
        return share;
    }
    /**
     * 审批并修改数据库状态,同时保存事务日志
     *
     * @author Liyanpeng
     * @date 2020/1/9 15:57
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer shareId, ShareAuditDTO auditDTO, String transactionId) {
        this.auditByIdInDB(shareId, auditDTO);

        this.rocketmqTransactionLogMapper.insert(
                RocketMqTransactionLog.builder()
                        .id(IdWorker.getIdStr())
                        .transactionId(transactionId)
                        .log("审核分享...")
                        .build()
        );
    }
    /**
     * 审批并修改数据库状态
     *
     * @author Liyanpeng
     * @date 2020/1/9 15:57
     **/
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        this.shareMapper.updateById(share);
    }
    /**
     * 发送事务消息
     *
     * @author Liyanpeng
     * @date 2020/1/9 15:57
     **/
    private void sendTransactionMessage(Share share, ShareAuditDTO auditDTO) {
        rocketMQTemplate.sendMessageInTransaction("tx-add-bonus-group",
                "user-info-topic",
                MessageBuilder.withPayload(UserAddBonusMsgDTO.builder()
                        .userId(share.getUserId())
                        .bonus(50)
                        .build())
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, IdWorker.getId())
                        .setHeader("share_id", share.getId())
                        .build(),
                auditDTO);
    }
    /**
     * 发送普通消息
     *
     * @author Liyanpeng
     * @date 2020/1/9 15:57
     **/
    private void sendNormalMessage(Share share) {
        rocketMQTemplate.convertAndSend("user-info-topic", UserAddBonusMsgDTO
                .builder()
                .userId(share.getUserId())
                .bonus(50)
                .build());
    }
}
