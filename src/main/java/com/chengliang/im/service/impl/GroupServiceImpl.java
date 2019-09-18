package com.chengliang.im.service.impl;

import com.chengliang.im.bean.Group;
import com.chengliang.im.mapper.GroupMapper;
import com.chengliang.im.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<Group> getGroupListByUserId(Integer userId) {
        return groupMapper.selectGroupByUserId(userId);
    }

    @Override
    public int getCountByGroupId(Integer id) {
//        Group group = new Group();
//        group.setId(id);
//        return groupMapper.selectCount(group);
        return groupMapper.selectCountByGroupId(id);
    }

}
