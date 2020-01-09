package com.user.centre.serivce.impl;

import com.user.centre.mapper.BonusEventLogMapper;
import com.user.centre.mapper.UserMapper;
import com.user.centre.model.dto.messaging.UserAddBonusMsgDTO;
import com.user.centre.model.entity.BonusEventLog;
import com.user.centre.model.entity.User;
import com.user.centre.serivce.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author novLi
 * @date 2019年12月31日 10:48
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BonusEventLogMapper bonusEventLogMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO message) {
        // 1. 为用户加积分
        Integer userId = message.getUserId();
        Integer bonus = message.getBonus();
        User user = userMapper.selectById(userId);

        user.setBonus(user.getBonus() + bonus);
        userMapper.updateById(user);

        // 2. 记录日志到bonus_event_log表里面
        this.bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event(message.getEvent())
                        .createTime(new Date())
                        .description(message.getDescription())
                        .build()
        );
        log.info("积分添加完毕...");

    }
}
