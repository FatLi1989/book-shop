package com.user.centre.serivce.impl;

import com.user.centre.mapper.UserMapper;
import com.user.centre.model.entity.User;
import com.user.centre.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author novLi
 * @date 2019年12月31日 10:48
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }
}
