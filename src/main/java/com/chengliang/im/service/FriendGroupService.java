package com.chengliang.im.service;

import com.chengliang.im.bean.FriendGroup;
import com.chengliang.im.bean.User;

import java.util.List;

/**
 * @author chengliang
 */
public interface FriendGroupService {

    List<FriendGroup> getFriendGroupListByUserId(Integer userId);

}
