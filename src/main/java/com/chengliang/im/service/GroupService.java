package com.chengliang.im.service;

import com.chengliang.im.bean.Group;

import java.util.List;

public interface GroupService {

    List<Group> getGroupListByUserId(Integer userId);

    /**
     * 通过群组ID查询群人数
     * @param id
     * @return
     */
    int getCountByGroupId(Integer id);

}
