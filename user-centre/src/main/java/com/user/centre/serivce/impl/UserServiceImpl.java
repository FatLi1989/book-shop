package com.user.centre.serivce.impl;

import com.user.centre.mapper.UserMapper;
import com.user.centre.model.entity.User;
import com.user.centre.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }


}
