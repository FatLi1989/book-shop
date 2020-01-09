package com.contain.centre.mq.rocketmq;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.contain.centre.mapper.RocketMqTransactionLogMapper;
import com.contain.centre.model.dto.content.ShareAuditDTO;
import com.contain.centre.model.entity.RocketMqTransactionLog;
import com.contain.centre.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * @author novLi
 * @date 2020年01月09日 15:40
 */

@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
public class RocketMqTransactionListener implements RocketMQLocalTransactionListener {


    @Autowired
    private ShareService shareService;

    @Autowired
    private RocketMqTransactionLogMapper rocketMqTransactionLogMapper;

    /**
     * 执行本地事务
     **/
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        log.info("分享内容审批执行本地事务 -> {} , 传参为 -> {}", message, arg);
        MessageHeaders messageHeaders = message.getHeaders();
        String transactionId = (String) messageHeaders.get(RocketMQHeaders.TRANSACTION_ID);
        String shareId = (String) messageHeaders.get("share_id");

        try {
            shareService.auditByIdWithRocketMqLog(Integer.valueOf(shareId), (ShareAuditDTO) arg, transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("分享内容审批执行本地事务发生异常 -> {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 本地事务检查
     **/
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders messageHeaders = message.getHeaders();
        String transactionId = (String) messageHeaders.get(RocketMQHeaders.TRANSACTION_ID);

        QueryWrapper<RocketMqTransactionLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("transaction_id", transactionId);

        if (ObjectUtil.isNotNull(rocketMqTransactionLogMapper.selectOne(queryWrapper))) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
