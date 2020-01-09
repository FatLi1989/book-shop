package com.user.centre.mq.rocketmq;

import com.user.centre.model.dto.messaging.UserAddBonusMsgDTO;
import com.user.centre.serivce.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author novLi
 * @date 2020年01月09日 13:48
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "user-info-group", topic = "user-info-topic")
public class AddBonusConsumer implements RocketMQListener<UserAddBonusMsgDTO> {

    @Autowired
    private UserService userService;

    @Override
    public void onMessage(UserAddBonusMsgDTO message) {
        log.info("用户添加积分消费开始");
        message.setEvent("CONTRIBUTE");
        message.setDescription("投稿加积分..");
        userService.addBonus(message);

    }
}
