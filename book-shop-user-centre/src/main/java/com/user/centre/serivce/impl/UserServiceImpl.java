package com.user.centre.serivce.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.user.centre.mapper.BonusEventLogMapper;
import com.user.centre.mapper.UserMapper;
import com.user.centre.model.dto.messaging.UserAddBonusMsgDTO;
import com.user.centre.model.dto.user.UserLoginDTO;
import com.user.centre.model.entity.BonusEventLog;
import com.user.centre.model.entity.User;
import com.user.centre.serivce.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    private final BonusEventLogMapper bonusEventLogMapper;

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

    @Override
    public User login(UserLoginDTO loginDTO, String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_id", openId);
        User user = this.userMapper.selectOne(queryWrapper);

        if (ObjectUtil.isNull(user)) {
            User userToSave = User.builder()
                    .id(IdWorker.getIdStr())
                    .wxId(openId)
                    .bonus(300)
                    .wxNickname(loginDTO.getWxNickname())
                    .avatarUrl(loginDTO.getAvatarUrl())
                    .roles("user")
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            this.userMapper.insert(userToSave);
            return userToSave;
        }
        return user;
    }
}
