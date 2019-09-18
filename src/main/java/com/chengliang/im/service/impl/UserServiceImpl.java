package com.chengliang.im.service.impl;

import com.chengliang.im.bean.User;
import com.chengliang.im.mapper.UserMapper;
import com.chengliang.im.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserListByGroupId(Integer id) {
        return userMapper.selectUserListByGroupId(id);
    }

    @Override
    public User getUserByGroupId(Integer id) {
        return userMapper.selectUserByGroupId(id);
    }

    @Override
    public List<Integer> getGroupIdsByLoginName(String loginName) {
        return userMapper.getGroupIdsByLoginName(loginName);
    }
}
