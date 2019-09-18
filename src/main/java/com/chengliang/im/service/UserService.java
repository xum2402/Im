package com.chengliang.im.service;

import com.chengliang.im.bean.User;

import java.util.List;

public interface UserService {

    /**
     * 通过群组ID 查询该群下有哪些用户
     * @param id
     * @return
     */
    List<User> getUserListByGroupId(Integer id);

    /**
     * 通过群组Id查询群主
     * @param id
     * @return
     */
    User getUserByGroupId(Integer id);


    /**
     * 通过登录名获取 该用户 有哪些群
     * @param loginName
     * @return
     */
    List<Integer> getGroupIdsByLoginName(String loginName);

}
