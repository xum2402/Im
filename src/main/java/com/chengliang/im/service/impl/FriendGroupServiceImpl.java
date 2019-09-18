package com.chengliang.im.service.impl;

import com.chengliang.im.bean.FriendGroup;
import com.chengliang.im.mapper.FriendGroupMapper;
import com.chengliang.im.service.FriendGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendGroupServiceImpl implements FriendGroupService {

    @Autowired
    private FriendGroupMapper friendGroupMapper;

    @Override
    public List<FriendGroup> getFriendGroupListByUserId(Integer userId) {
        return friendGroupMapper.selectFriendGroupByUserId(userId);
    }

}
